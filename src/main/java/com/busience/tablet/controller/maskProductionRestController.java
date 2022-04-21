package com.busience.tablet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.tablet.service.MaskProductionService;

@RestController("maskProductionRestController")
@RequestMapping("/tablet/maskProductionRest")
public class maskProductionRestController {

	@Autowired
	MaskProductionService maskProductionService;
	
	@GetMapping("/workingByMachine")
	public List<WorkOrderDto> workingByMachine(SearchDto searchDto){
		return maskProductionService.workingSelectByMachine(searchDto);
	}
	
}
