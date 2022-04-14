package com.busience.material.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotTransDto;
import com.busience.material.service.MatInOutService;

@RestController("matInoutLXReportRestController")
@RequestMapping("matInoutLXReportRest")
public class matInoutLXReportRestController {

	@Autowired
	MatInOutService matInOutService;
	
	@GetMapping("MIO_Select")
	public List<LotTransDto> MIO_Select(SearchDto searchDto) {
		return matInOutService.MatInOutSelect(searchDto);
	}
}
