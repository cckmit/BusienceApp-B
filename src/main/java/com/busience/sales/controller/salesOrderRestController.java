package com.busience.sales.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.SalesOrderListDto;
import com.busience.sales.dto.SalesOrderMasterDto;
import com.busience.sales.service.SalesOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesOrderRestController")
@RequestMapping("salesOrderRest")
public class salesOrderRestController {

	@Autowired
	SalesOrderService salesOrderService;

	// SalesOrderMaster select
	@GetMapping("/SO_Search")
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderMasterSelectDao(searchDto);
	}

	// SalesOrderList select
	@GetMapping("/SOL_Search")
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderListSelectDao(searchDto);
	}

	// save
	@PostMapping("/SOL_Save")
	public int SO_Save(@RequestParam("masterData") String masterData, @RequestParam("subData") String subData, Principal principal) {
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			SalesOrderMasterDto salesOrderMasterDto = mapper.readValue(masterData, SalesOrderMasterDto.class);

			List<SalesOrderListDto> salesOrderListDtoList = Arrays
					.asList(mapper.readValue(subData, SalesOrderListDto[].class));

			return salesOrderService.salesOrderInsertUpdate(salesOrderMasterDto, salesOrderListDtoList, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesOrderList delete
	@DeleteMapping("/SOL_Delete")
	public int SOL_Delete(@RequestBody List<SalesOrderListDto> salesOrderDtoList) {
		return salesOrderService.salesOrderListDeleteDao(salesOrderDtoList);				
	}
}
