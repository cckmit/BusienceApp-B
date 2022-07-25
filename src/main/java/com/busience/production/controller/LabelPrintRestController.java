package com.busience.production.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.LabelPrintDto;
import com.busience.production.service.LabelPrintService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("LabelPrintRestController")
@RequestMapping("LabelPrintRest")
public class LabelPrintRestController {
	
	@Autowired
	LabelPrintService labelPrintService;
	
	@PostMapping("/rawMaterialLabelSelect")
	public List<LabelPrintDto> rawMaterialLabelSelect(
			@RequestParam("selectedData") String selectedData,
			@RequestParam("warehouse") String string
		) {
		ObjectMapper mapper = new ObjectMapper();
	
		try {
			List<SearchDto> searchDtoList = Arrays.asList(mapper.readValue(selectedData, SearchDto[].class));
	
			String warehouse = mapper.readValue(string, String.class);
			
			return labelPrintService.rawMaterialLabelSelect(searchDtoList, warehouse);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
