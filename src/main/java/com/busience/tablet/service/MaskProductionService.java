 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class MaskProductionService {

	@Autowired
	WorkOrderDao workOrderDao;
	
	// 코드 조건으로 조회
	public List<WorkOrderDto> workingSelectByMachine(SearchDto searchDto) {
        return workOrderDao.workingSelectByMachineDao(searchDto);
	}
	
}
