package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dao.TransDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.TransDto;

@Service
public class MatOutReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;

	@Autowired
	TransDao transDao;
	
	@Autowired
	OutMatDao outMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public List<LotMasterDto> matOutReturnSelect(SearchDto searchDto){
		return lotMasterDao.lotMasterSelectDao(searchDto);
	}
	
	//저장
	public int matOutReturnSave(List<LotMasterDto> lotMasterDtoList, String userCode){
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<lotMasterDtoList.size();i++) {
						LotMasterDto lotMasterDto = lotMasterDtoList.get(i);

						String lotNo = lotMasterDto.getLM_LotNo();
						int no = lotTransDao.lotTransNoSelectDao(lotNo);
						String itemCode = lotMasterDto.getLM_ItemCode();
						double qty = lotMasterDto.getLM_TransQty();
						String before = lotMasterDto.getLM_Warehouse();
						String after = "50";
						String classfy = "210";
						
						//랏마스터 업데이트
						lotMasterDao.salesLotMasterInsertUpdateDao(
								lotNo, itemCode, (-1)*qty, before
								);
						//재고 업데이트
						stockDao.stockInsertUpdateDao(itemCode, (-1)*qty, before);
						
						//트랜스
						transDao.transInsertDao(
								no, lotNo, itemCode, qty, before, after, classfy
								);
						
						//랏트랜스
						lotTransDao.lotTransInsertDao(
								no, lotNo, itemCode, qty, before, after, classfy
								);
						
						//랏마스터
						lotMasterDao.salesLotMasterInsertUpdateDao(
								lotNo, itemCode, qty, after
								);
						
						//재고
						stockDao.stockInsertUpdateDao( itemCode, qty, after);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//등록
	public List<TransDto> transSelect(SearchDto searchDto){
		return transDao.transSelectDao(searchDto);
	}
}
