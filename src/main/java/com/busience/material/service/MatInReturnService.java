package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.MatInputDao;
import com.busience.material.dto.InMatDto;

@Service
public class MatInReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	MatInputDao matInputDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public List<InMatDto> matInReturnSelect(SearchDto searchDto){
		return matInputDao.matInReturnSelectDao(searchDto);
	}
	
	//저장
	public int matInReturnSave(List<InMatDto> inMatDtoList, String userCode){
		//랏마스터 저장
		//랏트랜스 저장
		//입고저장
		//자재입고
		//재고
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<inMatDtoList.size();i++) {
						InMatDto inMatDto = inMatDtoList.get(i);
						//작업자
						inMatDto.setInMat_Modifier(userCode);
						
						//랏번호가 없을경우 랏번호 생성
						if(inMatDto.getInMat_Lot_No() == null || inMatDto.getInMat_Lot_No().isBlank()) {
							String LotNo = matInputDao.LotNoSelectDao(inMatDto);
							inMatDto.setInMat_Lot_No(LotNo);

							//랏번호 증가
							matInputDao.MatLotNoUpdateDao();
						}
						
						//랏트랜스번호 가져오기
						int LotTransNo = matInputDao.LotTransNoSelectDao(inMatDto);
						inMatDto.setInMat_No(LotTransNo);
						
						//이동 설정하기
						inMatDto.setInMat_Before(wareHouseList.get(0).getCHILD_TBL_NO());
						inMatDto.setInMat_After("");
						
						inMatDto.setInMat_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						
						//입고 반품
						inMatDto.setInMat_Rcv_Clsfc("207");

						//랏트랜스
						matInputDao.LotTransInsertDao(inMatDto);

						//반품은 수량에 -를 붙인다
						inMatDto.setInMat_Qty(-1*inMatDto.getInReturn_Qty());
						
						//랏마스터
						matInputDao.LotMasterInsertDao(inMatDto);						
						
						//자재입고
						matInputDao.InMatInsertDao(inMatDto);
						
						//재고
						matInputDao.StockMatUpdateDao(inMatDto);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
