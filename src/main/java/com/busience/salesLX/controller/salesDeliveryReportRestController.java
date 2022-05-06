package com.busience.salesLX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.common.service.HometaxApiService;
import com.busience.salesLX.dto.Sales_OutMat_tbl;
import com.busience.salesLX.service.SalesOutputService;

@RestController("salesDeliveryReportRestController")
@RequestMapping("salesDeliveryReportRest")
public class salesDeliveryReportRestController {

	@Autowired
	HometaxApiService hometaxApiService;
	
	@Autowired
	SalesOutputService salesOutputService;
	
	
	// 납품 현황 조회(거래처 출고)
	@GetMapping("/SOCL_Search")
	public List<Sales_OutMat_tbl> salesDeliveryCustomerViewDao(SearchDto searchDto) {
		return salesOutputService.salesDeliveryCustomerViewDao(searchDto);
	}
	
	// 납품 현황 조회(거래처 리스트)
	@GetMapping("/SDL_Search")
	public List<Sales_OutMat_tbl> salesDeliveryList(SearchDto searchDto) {
		return salesOutputService.salesDeliveryList(searchDto);
	}
			
	// 납품 현황 (거래처별명세서)
	@GetMapping("/SDC_Search")
	public List<Sales_OutMat_tbl> salesDeliveryCustomerDao(SearchDto searchDto) {
		return salesOutputService.salesDeliveryCustomerDao(searchDto);
	}
	
	// hometaxApiDataSave
	@PostMapping("/hometaxApiDataSave")
	public int hometaxApiDataSave(SearchDto searchDto) {
		return hometaxApiService.hometaxApiDataSave(searchDto);
	}
}
