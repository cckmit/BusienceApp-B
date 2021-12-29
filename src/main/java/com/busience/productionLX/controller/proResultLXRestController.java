package com.busience.productionLX.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.productionLX.service.ProResultService;

@RestController("proResultLXRestController")
@RequestMapping("proResultLXRest")
public class proResultLXRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ProResultService proResultService;
	
	// proResultSelect
	@GetMapping("/proResultSelect")
	public List<ProductionMgmtDto> proResultSelect(SearchDto searchDto) {
		return proResultService.proResultSelect(searchDto);
	}
	
	@GetMapping("/workOrderDetail")
	public WorkOrderDto workOrderDetail(WorkOrderDto workOrderDto) {
		return proResultService.workOrderDetail(workOrderDto);
	}
	
	@PutMapping("/proResultUpdate")
	public int proResultUpdate(@RequestBody List<ProductionMgmtDto> productionMgmtDtoList) {
		return proResultService.proResultUpdate(productionMgmtDtoList);
	}
}
