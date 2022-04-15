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
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.OutMatDto;

@Service
public class MatOutReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	OutMatDao outMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public List<OutMatDto> matOutReturnSelect(SearchDto searchDto){
		return outMatDao.outMatReturnSelectDao(searchDto);
	}
	
	//저장
	public int matOutReturnSave(List<OutMatDto> outMatDtoList, String userCode){
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<outMatDtoList.size();i++) {
						OutMatDto outMatDto = outMatDtoList.get(i);
						//작업자
						outMatDto.setOM_Modifier(userCode);
						
						//외부 창고 관련 업데이트 후에 생산 창고 관련 인서트 or 업데이트
						
						//랏마스터 업데이트
						lotMasterDao.lotMasterUpdateDao(outMatDtoList.get(i));
						//재고 업데이트
						stockDao.stockUpdateDao(outMatDtoList.get(i));
						
						//랏트랜스번호 가져오기
						int LotTransNo = lotTransDao.lotTransNoSelectDao2(outMatDtoList.get(i));
						outMatDto.setOM_No(LotTransNo);
						
						//이동 설정하기 외부창고 -> 자재창고
						outMatDto.setOM_Before(outMatDto.getOM_WareHouse());
						outMatDto.setOM_After(wareHouseList.get(0).getCHILD_TBL_NO());
						
						outMatDto.setOM_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						
						//랏트랜스
						lotTransDao.lotTransInsertDao2(outMatDtoList.get(i));
						
						//입고
												
						//재고
						stockDao.stockInsertDao(outMatDtoList.get(i));
						
						//랏마스터 등록
						lotMasterDao.lotMasterInsertDao(outMatDtoList.get(i));
						
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
