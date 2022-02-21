package com.busience.monitoring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.EquipMonitoringDto;
import com.busience.monitoring.dto.TempChartDto;
import com.busience.monitoring.service.TemperatureMonitoringService;

@RestController
public class temperatureMonitoringRestController {

	@Autowired
	TemperatureMonitoringService temperatureMonitoringService;
	
	@GetMapping("/selectEquipMonitoring")
	public List<EquipMonitoringDto> selectEquipMonitoring (SearchDto searchDto){
		return temperatureMonitoringService.selectEquipMonitoring(searchDto);
	}
	
	@GetMapping("/tempChartDatas")
	public List<TempChartDto> tempChartDatas (SearchDto searchDto){
		return temperatureMonitoringService.tempChartDatas(searchDto);
	}
	
	@GetMapping("/updateTemperature")
	public int updateTemperature (EquipMonitoringDto equipMonitoringDto){
		return temperatureMonitoringService.updateTemperature(equipMonitoringDto);
	}
}
