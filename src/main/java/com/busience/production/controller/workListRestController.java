package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.common.service.DtlService;
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkListService;

@RestController("workListRestController")
@RequestMapping("workListRest")
public class workListRestController {

	@Autowired
	WorkListService workListService;
	
	@Autowired
	DtlService dtlService;
	
	// 작업 현황
	@GetMapping("/workListSearch")
	public List<WorkOrderDto> workListSearch(SearchDto searchDto) {
		String[] statusCodeArr = {dtlService.getAllDtl(29).get(1).getCHILD_TBL_NO(), dtlService.getAllDtl(29).get(2).getCHILD_TBL_NO()};;
		searchDto.setStatusCodeArr(statusCodeArr);
		return workListService.workListSelect(searchDto);
	}
	
	// 작업 현황
	@PostMapping("/workListSave")
	public int workListSave(WorkOrderDto workOrderDto) {
		return workListService.workListSave(workOrderDto);
	}
}
