package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dto.DefectDto;
import com.busience.qc.service.DefectInsertService;

@RestController
@RequestMapping("defectInsertRest")
public class defectInsertRestController {
	
	@Autowired
	DefectInsertService defectInsertService;
		
	@GetMapping("/DI_Search")
	public List<WorkOrderDto> DI_Search(SearchDto searchDto){
		return defectInsertService.completeWorkOrder(searchDto);
	}
	
	@GetMapping("/DIS_Search")
	public List<DefectDto> DIS_Search(WorkOrderDto workOrderDto){
		return defectInsertService.defectList(workOrderDto.getWorkOrder_ONo());
	}
	
	@PostMapping("/DI_Save")
	public int DI_Save(@RequestBody List<DefectDto> defectDtoList) {
		return defectInsertService.defectSave(defectDtoList);
	}
	
}
