package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.LabelPrintDto;
import com.busience.production.service.LabelPrintService;

@RestController("LabelPrintRestController")
@RequestMapping("LabelPrintRest")
public class LabelPrintRestController {
	
	@Autowired
	LabelPrintService labelPrintService;
	
	@PostMapping("/rawMaterialLabelSelect")
	public List<LabelPrintDto> rawMaterialLabelSelect(@RequestBody List<SearchDto> searchDtoList) {
		System.out.println(searchDtoList);
		return labelPrintService.rawMaterialLabelSelect(searchDtoList);
	}
}
