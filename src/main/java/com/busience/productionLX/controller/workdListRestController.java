package com.busience.productionLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.productionLX.service.WorkOrderService;

@RestController("workdListRestController")
@RequestMapping("workdListRest")
public class workdListRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	WorkOrderService workOrderService;

	@Autowired
	JdbcTemplate jdbctemplate;
	
	// 세부 작업 현황
	@GetMapping("/MI_Search")
	public List<WorkOrder_tbl> workdListSearch(SearchDto searchDto) {
		return workOrderService.workdListSearch(searchDto);
	}

}
