package com.busience.monitoring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class ProMonitoringService {

	@Autowired
	WorkOrderDao workOrderDao;
	
	public List<WorkOrderDto> workOrderMonitoringSelect(SearchDto searchDto){
		return workOrderDao.workOrderMonitoringSelectDao(searchDto);
	}
}
