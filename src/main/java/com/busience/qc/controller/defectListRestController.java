package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.qc.dto.DefectDto;
import com.busience.qc.service.DefectListService;

@RestController
@RequestMapping("defectListRest")
public class defectListRestController {

	@Autowired
	DefectListService defectListService;
	
	@GetMapping("DL_Search")
	public List<WorkOrderDto> DL_Search(SearchDto searchDto) {
		return defectListService.defectListSelectDao(searchDto);
	}
	
	@GetMapping("DLS_Search")
	public List<DefectDto> DLS_Search(SearchDto searchDto) {
		return defectListService.defectListSub(searchDto);
	}
}
