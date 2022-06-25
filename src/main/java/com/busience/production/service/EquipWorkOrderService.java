package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dto.EquipWorkOrderDto;

@Service
public class EquipWorkOrderService {

	@Autowired
	EquipWorkOrderDao equipWorkOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	public List<EquipWorkOrderDto> equipWorkOrderSelect(SearchDto searchDto) {
		return equipWorkOrderDao.equipWorkOrderSelectDao(searchDto);
	}
	
	public int equipWorkOrderUpdate(List<EquipWorkOrderDto> workOrderDtoList) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					for (int i = 0; i < workOrderDtoList.size(); i++) {
						
						if(workOrderDtoList.get(i).getWorkOrder_ItemCode() == null || workOrderDtoList.get(i).getWorkOrder_ItemCode() == "") {
							equipWorkOrderDao.equipWorkOrderInsert(workOrderDtoList.get(i));
						} else {
							equipWorkOrderDao.equipWorkOrderUpdate(workOrderDtoList.get(i));
						}
					}
				}
			});
			
			return 1;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}