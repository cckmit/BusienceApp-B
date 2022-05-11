package com.busience.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.dto.SearchDto;
import com.busience.standard.service.MachineService;

@Controller
public class contolController {
	
	@Autowired
	MachineService machineService;

	@Autowired
	JdbcTemplate jdbctemplate;

	// proMonitoring
	@GetMapping("proMonitoring")
	public String proMonitoring() {
		return "normal/monitoring/proMonitoring";
	}
	
	// TemperatureMonitoring
	@GetMapping("TemperatureMonitoring")
	public String TemperatureMonitoring(Model model, SearchDto searchDto) {
		if(searchDto.getMachineCode() == null) {
			searchDto.setMachineCode("M001");
		}
		// 설비 리스트 가져오기
		model.addAttribute("machineList", machineService.selectMachineList());
		model.addAttribute("machineCode", searchDto.getMachineCode());
		return "normal/monitoring/TemperatureMonitoring";
	}
}
