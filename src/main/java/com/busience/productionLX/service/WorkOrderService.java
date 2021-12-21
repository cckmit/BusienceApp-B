package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class WorkOrderService {
	
	@Autowired
	WorkOrderDao workOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<ProductionMgmtDto> proResultSelect(SearchDto searchDto) {		
		return workOrderDao.proResultSelectDao(searchDto);
	}
	
	public WorkOrderDto workOrderDetail(WorkOrderDto workOrderDto) {		
		return workOrderDao.workOrderDetailDao(workOrderDto);
	}
		
	public int proResultUpdate(List<ProductionMgmtDto> productionMgmtDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<productionMgmtDtoList.size();i++) {
						workOrderDao.proResultUpdateDao(productionMgmtDtoList.get(i));
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
