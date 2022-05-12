package com.busience.monitoring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkOrderService;

@RestController
@RequestMapping("proMonitoringRest")
public class proMonitoringRestController {

	@Autowired
	WorkOrderService workOrderService;
	
	@GetMapping("workOrderMonitoringSelect")
	public List<WorkOrderDto> workOrderMonitoringSelect(SearchDto searchDto){
		return workOrderService.workOrderMonitoringSelect(searchDto);
	}
}
