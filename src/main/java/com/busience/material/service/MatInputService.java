package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.material.dao.MatInputDao;
import com.busience.material.dto.InMatDto;

@Service
public class MatInputService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	MatInputDao matInputDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public int matInputRegister(List<InMatDto> InMatDtoList, String userCode){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<InMatDtoList.size();i++) {
						InMatDto inMatDto = InMatDtoList.get(i);
						//작업자
						inMatDto.setInMat_Modifier(userCode);
						
						//랏번호가 없을경우 랏번호 생성
						if(inMatDto.getInMat_Lot_No().length() == 0) {
							String LotNo = matInputDao.LotNoSelectDao(inMatDto);
							inMatDto.setInMat_Lot_No(LotNo);							
						}
						
						//랏트랜스번호 가져오기
						int LotTransNo = matInputDao.LotTransNoSelectDao(inMatDto);
						inMatDto.setInMat_No(LotTransNo);
						
						//이동 설정하기
						inMatDto.setInMat_Before("");
						inMatDto.setInMat_After(wareHouseList.get(0).getCHILD_TBL_NO());
						
						inMatDto.setInMat_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());

						//랏마스터
						matInputDao.LotMasterInsertDao(inMatDto);
						
						//랏트랜스
						matInputDao.LotTransInsertDao(inMatDto);
						
						//자재입고
						matInputDao.inMatInsertDao(inMatDto);
						
						//발주리스트
						matInputDao.orderListUpdateDao(inMatDto);
						
						//재고
						matInputDao.stockMatUpdateDao(inMatDto);
						
						//발주마스터
						matInputDao.matOrderMasterUpdateDao(inMatDto);
						
						//랏번호
						matInputDao.matLotNoUpdateDao();
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
