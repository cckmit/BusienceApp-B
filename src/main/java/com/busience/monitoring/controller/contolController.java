package com.busience.monitoring.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	public String proMonitoring(Model model) {
		model.addAttribute("pageName", "생산현황 모니터링");

		model.addAttribute("CHILD_TBL_TYPE", jdbctemplate
				.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt("CHILD_TBL_TYPE");

						if (num > 1 && num % 2 != 0)
							--num;

						if (num > 8)
							num = 8;

						return String.valueOf(num);
					}
				}));

		return "normal/monitoring/proMonitoring";
	}
	
	// workMonitoring
	@GetMapping("workMonitoring")
	public String workMonitoring(Model model) {

		model.addAttribute("CHILD_TBL_TYPE", jdbctemplate
				.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt("CHILD_TBL_TYPE");

						if (num > 1 && num % 2 != 0)
							--num;

						if (num > 8)
							num = 8;

						return String.valueOf(num);
					}
				}));

		return "normal/monitoring/workMonitoring";
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

	// equipMonitoring
	@GetMapping("equipMonitoring")
	public String equipMonitoring() {
		return "normal/monitoring/equipMonitoring";
	}

	@GetMapping("workorderoi")
	public String orderMaster() {
		return "normal/monitoring/workorderoi";
	}
	
	@GetMapping("tempMonitoringTest")
	public String tempMonitoringTest() {
		return "monitoring/tempMonitoringTest";
	}

}
