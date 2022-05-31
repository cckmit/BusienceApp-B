package com.busience.sales.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.dto.Sales_OutMat_tbl;
import com.busience.sales.service.SalesOutReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesOutReturnRestController")
@RequestMapping("salesOutReturnRest")
public class salesOutReturnRestController {
	
	@Autowired
	SalesOutReturnService salesOutReturnService;
	
	 // 반품 저장
	@PostMapping("/SORI_Save") 
	public int salesOutReturnInsert(@RequestParam("dataList") String dataList, Principal principal) {
		
		System.out.println("dataList = " + dataList);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			List<Sales_OutMat_tbl> salesOutReturnDtoList = Arrays.asList(mapper.readValue(dataList, Sales_OutMat_tbl[].class));
			
			return salesOutReturnService.salesOutReturnInsert(salesOutReturnDtoList, principal.getName());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
	}
	 
}
