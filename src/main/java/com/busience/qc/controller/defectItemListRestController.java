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
@RequestMapping("defectItemListRest")
public class defectItemListRestController {
	
	@Autowired
	DefectListService defectItemListService;
		
	@GetMapping("/DIL_Search")
	public List<WorkOrderDto> DIL_Search(SearchDto searchDto){
		return defectItemListService.defectItemListSelect(searchDto);
	}
	
	@GetMapping("/DILS_Search")
	public List<DefectDto> DILS_Search(SearchDto searchDto){
		return defectItemListService.defectListSub(searchDto);
	}
		
}
