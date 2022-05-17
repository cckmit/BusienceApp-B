package com.busience.sales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotTransDto;
import com.busience.material.service.MatInOutService;

@RestController("salesInoutReportRestController")
@RequestMapping("salesInoutReportRest")
public class salesInoutReportRestController {

	@Autowired
	MatInOutService matInOutService;
	
	@GetMapping("/FIO_Select")
	public List<LotTransDto> inOutSalesSelectDao(SearchDto searchDto) {
		return matInOutService.inOutSalesSelectDao(searchDto);
	}
	
}
