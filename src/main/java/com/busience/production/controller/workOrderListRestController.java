package com.busience.production.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkOrderService;

@RestController("workOrderListRestController")
@RequestMapping("workOrderListRest")
public class workOrderListRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	// 작업지시 접수 상단 그리드
	@GetMapping("/workorderList")
	public List<WorkOrderDto> workorderListSelect(SearchDto searchDto) {
		return workOrderService.workorderListSelect(searchDto);
	}

	// workOrderListUpdate
	@PostMapping("/OrderUpdate")
	public int OrderUpdate(WorkOrderDto workOrderDto, Principal principal) {
		return workOrderService.workOrderListUpdate(workOrderDto, principal.getName());
	}
}
