package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.IqcDto;
import com.busience.qc.service.IqcRateService;

@RestController("iqcRateRestController")
@RequestMapping("iqcRateRest")
public class iqcRateRestController {

	@Autowired
	IqcRateService iqcRateService;
	
	//영업 출고검사 LotMaster 조회
	@GetMapping("iqcRateSelect")
	public List<IqcDto> iqcRateSelect(SearchDto searchDto) {
		List<IqcDto> InMatInspectDtoList = iqcRateService.iqcRateSelectDao(searchDto);
		return InMatInspectDtoList;
	}
}
