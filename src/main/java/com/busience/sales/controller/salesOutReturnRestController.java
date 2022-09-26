package com.busience.sales.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.dto.SalesOutMatDto;
import com.busience.sales.service.SalesOutReturnService;

@RestController("salesOutReturnRestController")
@RequestMapping("salesOutReturnRest")
public class salesOutReturnRestController {
	
	@Autowired
	SalesOutReturnService salesOutReturnService;
	
	 // 반품 저장
	@PostMapping("/SORI_Save") 
	public int salesOutReturnInsert(@RequestBody List<SalesOutMatDto> salesOutMatDtoList, Principal principal) {	
		return salesOutReturnService.salesOutReturnSave(salesOutMatDtoList, principal.getName());
	}
}
