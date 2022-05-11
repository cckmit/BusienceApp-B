package com.busience.monitoring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.service.ProMonitoringService;
import com.busience.productionLX.dto.WorkOrderDto;

@RestController
@RequestMapping("proMonitoringRest")
public class proMonitoringRestController {

	@Autowired
	ProMonitoringService proMonitoringService;
	
	@GetMapping("workOrderMonitoringSelect")
	public List<WorkOrderDto> workOrderMonitoringSelect(SearchDto searchDto){
		return proMonitoringService.workOrderMonitoringSelect(searchDto);
	}
}
