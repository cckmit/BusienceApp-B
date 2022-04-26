package com.busience.salesLX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.service.SalesInputService;

@RestController("salesInputRestController")
@RequestMapping("salesInputRest")
public class salesInputRestController {

	@Autowired
	SalesInputService salesInputService;
	
	// LotMaster select
	@GetMapping("/SIM_Search")
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto) {
		return salesInputService.salesInputLotMasterSelectDao(lotMasterDto);
	}
}
