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
import com.busience.material.dao.InMatDao;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.InMatDto;

@Service
public class MatInReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
	@Autowired
	InMatDao inMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public List<InMatDto> matInReturnSelect(SearchDto searchDto){
		return inMatDao.inMatReturnSelectDao(searchDto);
	}
	
	//저장
	public int matInReturnSave(List<InMatDto> inMatDtoList, String userCode){

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
							String LotNo = lotNoDao.lotNoSelectDao(inMatDto);
							inMatDto.setInMat_Lot_No(LotNo);

							//랏번호 증가
							lotNoDao.lotNoMatUpdateDao();
						}
						
						//랏트랜스번호 가져오기
						int LotTransNo = lotTransDao.lotTransNoSelectDao(inMatDto);
						inMatDto.setInMat_No(LotTransNo);
						
						//이동 설정하기
						inMatDto.setInMat_Before(wareHouseList.get(0).getCHILD_TBL_NO());
						inMatDto.setInMat_After("");
						
						inMatDto.setInMat_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						
						//입고 반품
						inMatDto.setInMat_Rcv_Clsfc("207");

						//랏트랜스
						lotTransDao.lotTransInsertDao(inMatDto);

						//반품은 수량에 -를 붙인다
						inMatDto.setInMat_Qty(-1*inMatDto.getInReturn_Qty());
						
						//랏마스터
						lotMasterDao.lotMasterInsertUpdateDao(inMatDto);						
						
						//자재입고
						inMatDao.inMatInsertDao(inMatDto);
						
						//재고
						stockDao.stockInsertUpdateDao(inMatDto);
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
