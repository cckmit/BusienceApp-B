 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.WorkOrderDao;
import com.busience.production.dto.WorkOrderDto;

@Service
public class MaskPackagingService {
	
	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<WorkOrderDto> maskPackagingSelect() {
		return workOrderDao.workOrderTabletSelectDao("330");
	}
	
	public List<WorkOrderDto> packagingLineListSelect(SearchDto searchDto) {
		return workOrderDao.packagingLineListSelectDao(searchDto);
	}
	
	public String smallPackagingSave(SearchDto searchDto) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//어떤 품목을 포장하는지 필요
					
					//포장설비 리스트를 가져옴
					//List<WorkOrderDto> packagingLine = workOrderDao.packagingLineListSelectDao(searchDto);
					
					//포장수량/설비갯수
					
					//분배된 포장수량을 설비별로 랏저장
				}
			});
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
