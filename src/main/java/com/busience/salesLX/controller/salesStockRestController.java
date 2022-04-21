package com.busience.salesLX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.StockDto;
import com.busience.material.service.StockService;

@RestController("salesStockRestController")
@RequestMapping("salesStockRest")
public class salesStockRestController {

	@Autowired
	StockService stockService;
	
	// stock select
	@GetMapping("salesStockSelect")
	public List<StockDto> salesStockSelect(SearchDto searchDto) {
		return stockService.StockSelect(searchDto);
	}
	
	// salesOutputStock select
	@GetMapping("salesOutputStockSelect")
	public List<StockDto> salesOutputStockDao(SearchDto searchDto) {
		return stockService.salesOutputStockDao(searchDto);
	}
	
	// salesOutputOrderStock select
	@GetMapping("/SOSS_Search")
	public List<StockDto> salesOutputOrderStockDao(SearchDto searchDto) {
		return stockService.salesOutputOrderStockDao(searchDto);
	}
}
