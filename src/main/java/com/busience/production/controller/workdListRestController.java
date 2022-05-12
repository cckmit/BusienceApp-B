package com.busience.production.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrder_tbl;
import com.busience.production.service.WorkOrderService;

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
