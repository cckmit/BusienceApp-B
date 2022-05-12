package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.EquipTemperatureHistoryDto;
import com.busience.production.service.TempDailyService;

@RestController("tempDailyRestController")
@RequestMapping("tempDailyRest")
public class tempDailyRestController {
	
	@Autowired
	TempDailyService tempDailyService;
	
	// proResultSelect
	@GetMapping("/tempListSelect")
	public List<EquipTemperatureHistoryDto> tempListSelect(SearchDto searchDto) {
		return tempDailyService.TempDailyList(searchDto);
	}
}
