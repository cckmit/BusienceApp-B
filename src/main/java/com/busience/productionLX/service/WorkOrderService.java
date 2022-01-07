package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class WorkOrderService {
	
	@Autowired
	WorkOrderDao workOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<WorkOrderDto> workOrderSelect(SearchDto searchDto) {		
		return workOrderDao.workOrderSelectDao(searchDto);
	}
	
	public int workOrderUpdate(WorkOrderDto workOrderDto) {
		
		if(workOrderDto.getWorkOrder_WorkStatus() == "245" && workOrderDto.getWorkOrder_RQty() == 0) {
			//생산수량이 0 일경우 작업지시 자체를 삭제
			return workOrderDao.workOrderDeleteDao(workOrderDto);
		}else {
			System.out.println("점검1");
			if(workOrderDto.getWorkOrder_WorkStatus() == "244") {
				System.out.println("점검2");
				if(workOrderDao.workOrderCountDao(workOrderDto) > 0) {
					System.out.println("점검3");
					return 0;
				}
			}
			//그외의 경우 작업 완료 처리
			return workOrderDao.workOrderUpdateDao(workOrderDto);
		}
		
	}
}
