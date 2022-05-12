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
@RequestMapping("defectMachineListRest")
public class defectMachineListRestController {
	
	@Autowired
	DefectListService defectListService;
		
	@GetMapping("/DML_Search")
	public List<WorkOrderDto> DIL_Search(SearchDto searchDto){
		return defectListService.defectMachineListSelect(searchDto);
	}
	
	@GetMapping("/DMLS_Search")
	public List<DefectDto> DILS_Search(SearchDto searchDto){
		return defectListService.defectListSub(searchDto);
	}
		
}
