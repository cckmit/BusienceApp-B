package com.busience.tablet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkOrderSeiyonService;

@RestController("workOrderSeiyonRestController")
@RequestMapping("workOrderSeiyonRest")
public class workOrderSeiyonRestController {

	@Autowired
	WorkOrderSeiyonService workOrderSeiyonService;
	
	@GetMapping("WOS_Complete")
	public int WOS_Complete(WorkOrderDto workOrderDto) {
		return workOrderSeiyonService.WorkOrderSeiyonUpdate(workOrderDto);
	}
	
	@GetMapping("WOS_NewUpdate")
	public int WOS_NewUpdate(WorkOrderDto workOrderDto) {
		return workOrderSeiyonService.WorkOrderSeiyonNewUpdate(workOrderDto);
	}
}
