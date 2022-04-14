package com.busience.material.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.StockDto;
import com.busience.material.service.StockService;

@RestController("matStockRestController")
@RequestMapping("matStockRest")
public class matStockRestController {

	@Autowired
	StockService stockService;
	
	// 현재고현황
	@GetMapping("matStockSelect")
	public List<StockDto> matStockSelect(SearchDto searchDto) {		
		return stockService.StockSelect(searchDto);
	}

}
