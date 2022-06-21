package com.busience.material.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.material.dto.StockDto;
import com.busience.material.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	// 재고 Lot- 품목 조회
	@GetMapping("matStockLotSelect")
	public List<StockDto> stockLotSelectDao(SearchDto searchDto) {
		return stockService.stockLotSelectDao(searchDto);
	}
	
	// 재고 조정
	@GetMapping("matStockChangeSelect")
	public List<StockDto> matStockChanageSelect(StockDto stockDto) {
		return stockService.StockChanageSelect(stockDto);
	}
	
	// 재고 조정 저장
	@PostMapping("matStockChangeSave")
	public int matStockChangeSave(@RequestParam("masterData") String stockList,  @RequestParam("requestlistData") String requestSubList, Principal principal) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			List<StockDto> stockDtoList = Arrays.asList(mapper.readValue(stockList, StockDto[].class));
			
			List<RequestSubDto> requestSubDtoList = Arrays.asList(mapper.readValue(requestSubList, RequestSubDto[].class));
			
			
			return stockService.StockChangeSave(stockDtoList, requestSubDtoList, principal.getName());
			
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
