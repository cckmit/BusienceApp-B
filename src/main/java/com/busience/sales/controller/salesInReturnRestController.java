package com.busience.sales.controller;

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
import com.busience.material.dto.LotMasterDto;
import com.busience.sales.dto.SalesInMatDto;
import com.busience.sales.dto.Sales_InMat_tbl;
import com.busience.sales.service.SalesInReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesInReturnRestController")
@RequestMapping("salesInReturnRest")
public class salesInReturnRestController {
	
	@Autowired
	SalesInReturnService salesInReturnService;
	
	// 반품 조회
	@GetMapping("/salesInReturnLXSelect")
	public List<LotMasterDto> salesInReturnLXSelect() {
		return salesInReturnService.salesInReturnLXSelect();
	}
	
	// 반품 조회
	@GetMapping("/salesInReturnListLXSelect")
	public List<SalesInMatDto> salesInReturnListLXSelect(SearchDto searchDto) {
		return salesInReturnService.salesInReturnListLXSelect(searchDto);
	}
	
	// 반품 조회
	@GetMapping("/SIRI_Search")
	public List<Sales_InMat_tbl> salesInMatReturnListDao(SearchDto searchDto) {
		return salesInReturnService.salesInMatReturnListDao(searchDto);
	}
	
	// 반품 저장
	@PostMapping("/salesInReturnInsert")
	public int salesInReturnInsert(@RequestBody List<LotMasterDto> lotMasterDtoList, Principal principal) {
		return salesInReturnService.salesInReturnSave(lotMasterDtoList, principal.getName());
	}
	
	// 반품 저장
	@PostMapping("/SIRI_Save")
	public int salesInReturnInsert(@RequestParam("dataList") String dataList, Principal principal) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			List<Sales_InMat_tbl> salesInReturnDtoList = Arrays.asList(mapper.readValue(dataList, Sales_InMat_tbl[].class));
			
			return salesInReturnService.salesInReturnInsert(salesInReturnDtoList, principal.getName());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
	}
}
