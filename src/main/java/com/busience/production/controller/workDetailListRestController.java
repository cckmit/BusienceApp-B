package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkOrderService;

@RestController("workDetailListRestController")
@RequestMapping("workDetailListRest")
public class workDetailListRestController {

	@Autowired
	WorkOrderService workOrderService;

	// 세부 작업 현황
	@GetMapping("/workDetailListSearch")
	public List<WorkOrderDto> workDetailListSearch(SearchDto searchDto) {
		return workOrderService.workDetailListSearch(searchDto);
	}

}
