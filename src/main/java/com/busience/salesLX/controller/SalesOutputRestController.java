package com.busience.salesLX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.service.SalesOutputService;

@RestController("SalesOutputRestController")
@RequestMapping("salesOutputRest")
public class SalesOutputRestController {
	
	@Autowired
	SalesOutputService salesOutputService;
	
	// 영업 LotMaster 조회
	@GetMapping("/SLM_Search")
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto) {
		return salesOutputService.salesOutputLotMasterDao(searchDto);
	}

}
