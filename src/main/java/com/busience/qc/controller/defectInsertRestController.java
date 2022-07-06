package com.busience.qc.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.qc.dto.DefectDto;
import com.busience.qc.service.DefectInsertService;
import com.busience.tablet.dto.CrateLotDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("defectInsertRest")
public class defectInsertRestController {
	
	@Autowired
	DefectInsertService defectInsertService;
		
	@GetMapping("/DI_Search")
	public List<WorkOrderDto> DI_Search(SearchDto searchDto){
		return defectInsertService.completeWorkOrder(searchDto);
	}
	
	// 불량 list 조회
	@GetMapping("/DefectList")
	public List<CrateLotDto> defectListDao(SearchDto searchDto) {
		return defectInsertService.defectListDao(searchDto);
	}
	
	@GetMapping("/DIS_Search")
	public List<DefectDto> DIS_Search(WorkOrderDto workOrderDto){
		return defectInsertService.defectList(workOrderDto.getWorkOrder_ONo());
	}
	
	@PostMapping("/DI_Save")
	public int DI_Save(@RequestParam("defectList") String defectDtoList, @RequestParam("crateLotList") String crateList) {
		
		System.out.println(defectDtoList);
		System.out.println(crateList);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			CrateLotDto crateLotDto = mapper.readValue(crateList, CrateLotDto.class);
			
			List<DefectDto> defectList = Arrays.asList(mapper.readValue(defectDtoList, DefectDto[].class));
			
			return defectInsertService.defectSave(crateLotDto, defectList);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
