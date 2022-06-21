 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
}
