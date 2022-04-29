package com.busience.salesLX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.service.SalesPackingService;

@RestController("salesPackingRestController")
@RequestMapping("salesPackingRest")
public class salesPackingRestController {
	
	@Autowired
	SalesPackingService salesPackingService;
	
	// sales_packing_select
	@GetMapping("/SP_Search")
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto) {
		return salesPackingService.salesPackingListSelectDao(searchDto);
	}
}
