package com.busience.monitoring.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class contolController {
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	//proMonitoring
	@GetMapping("proMonitoring")
	public String proMonitoring(Model model) {
		model.addAttribute("pageName", "생산현황 모니터링");
		
		model.addAttribute("CHILD_TBL_TYPE",jdbctemplate.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHILD_TBL_TYPE");
			}
		}));
		
		return "normal/monitoring/proMonitoring";
	}
	
	//defectMonitoring
	@GetMapping("defectMonitoring")
	public String defectMonitoring(Model model) {
		model.addAttribute("pageName", "불량현황 모니터링");
		
		model.addAttribute("CHILD_TBL_TYPE",jdbctemplate.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHILD_TBL_TYPE");
			}
		}));
		
		return "normal/monitoring/defectMonitoring";
	}
	
	//workMonitoring
	@GetMapping("workMonitoring")
	public String workMonitoring(Model model) {
		
		model.addAttribute("CHILD_TBL_TYPE",jdbctemplate.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHILD_TBL_TYPE");
			}
		}));
		
		return "normal/monitoring/workMonitoring";
	}
	
	//equipMonitoring
	@GetMapping("equipMonitoring")
	public String equipMonitoring() {
		return "normal/monitoring/equipMonitoring";
	}
	
	@GetMapping("workorderoi")
	public String orderMaster() {
		return "normal/monitoring/workorderoi";
	}
	
}
