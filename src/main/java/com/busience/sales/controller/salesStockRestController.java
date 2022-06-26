package com.busience.sales.controller;

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
		return stockService.salesStockSelectDao(searchDto);
	}

	// 재고 Lot- 품목 조회
	@GetMapping("salesStockLotSelect")
	public List<StockDto> salesstockLotSelectDao(SearchDto searchDto) {
		return stockService.salesStockLotSelectDao(searchDto);
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
