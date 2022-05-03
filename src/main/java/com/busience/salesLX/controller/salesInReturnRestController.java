package com.busience.salesLX.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.service.SalesInReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesInReturnRestController")
@RequestMapping("salesInReturnRest")
public class salesInReturnRestController {
	
	@Autowired
	SalesInReturnService salesInReturnService;
	
	// 반품 저장
	@PostMapping("/SIRI_Save")
	public int salesInReturnInsert(@RequestParam("dataList") String dataList, Principal principal) {
		
		System.out.println("dataList = " + dataList);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			List<SalesPackingDto> salesInReturnDtoList = Arrays.asList(mapper.readValue(dataList, SalesPackingDto[].class));
			
			return salesInReturnService.salesInReturnInsert(salesInReturnDtoList, principal.getName());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}

}
