package com.busience.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.service.ProductionStatusService;
import com.busience.tablet.dto.CrateLotDto;

@RestController
@RequestMapping("productionStatusRest")
public class productionStatusRestController {

	@Autowired
	ProductionStatusService productionStatusService;
	
	@GetMapping("productionStatusSelect")
	public CrateLotDto productionStatusSelect(SearchDto searchDto){
		return productionStatusService.productionStatusSelect(searchDto);
	}
}
