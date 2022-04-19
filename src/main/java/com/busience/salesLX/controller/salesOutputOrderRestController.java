package com.busience.salesLX.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOutputOrderListDto;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;
import com.busience.salesLX.service.SalesOutputOrderMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesOutputOrderRestController")
@RequestMapping("salesOutputOrderRest")
public class salesOutputOrderRestController {

	@Autowired
	SalesOutputOrderMasterService salesOutputOrderMasterService;
	
	@GetMapping("/SOO_Search")
	public List<SalesOutputOrderMasterDto> salesOutputOrderMasterSelectDao(SearchDto searchDto) {
		return salesOutputOrderMasterService.salesOutputOrderMasterSelectDao(searchDto);
	}
	
	@PostMapping("/SOO_Save")
	public int SOO_Save(@RequestParam("masterData") String masterData, @RequestParam("subData") String subData, Principal principal) {
		System.out.println("masterData = " + masterData);
		System.out.println("subData = " + subData);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			SalesOutputOrderMasterDto salesOutputOrderMasterDto = mapper.readValue(masterData, SalesOutputOrderMasterDto.class);
			
			List<SalesOutputOrderListDto> SalesOutputOrderListDtoList = Arrays.asList(mapper.readValue(subData, SalesOutputOrderListDto[].class));
			
			return salesOutputOrderMasterService.salesOutputOrderInsert(salesOutputOrderMasterDto, SalesOutputOrderListDtoList, principal.getName());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return 0;
		}
	
	}
	
}
