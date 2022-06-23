package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.qc.dao.ItemPackingInspectDao;
import com.busience.qc.dto.ItemPackingInspectDto;

@Service
public class ItemPackingInspectService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	ItemPackingInspectDao itemPackingInspectDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 완제품 데이터 조회
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto) {
		return lotMasterDao.salesItemListDao(searchDto);
	}
	
	// LotNo로 조회
	public List<ItemPackingInspectDto> itemPackingInspectListDao(SearchDto searchDto) {
		return itemPackingInspectDao.itemPackingInspectListDao(searchDto);
	}
	
	// list
	public List<ItemPackingInspectDto> itemPackingInspectSearchDao(SearchDto searchDto) {
		return itemPackingInspectDao.itemPackingInspectSearchDao(searchDto);
	}
	
	public int itemPackingInspectInsertDao(List<ItemPackingInspectDto> dataList, ItemPackingInspectDto itemPackingInspectDto) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					
					for(int i=0; i<dataList.size(); i++) {
						//System.out.println(dataList.get(i));
						itemPackingInspectDao.itemPackingInspectInsertDao(dataList.get(i));
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
