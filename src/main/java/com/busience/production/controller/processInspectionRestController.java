package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProcessInspectDto;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.service.ProcessInspectionService;
import com.busience.tablet.dto.CrateLotDto;

@RestController("processInspectionRestController")
@RequestMapping("processInspectionRest")
public class processInspectionRestController {

	@Autowired
	ProcessInspectionService processInspectionService;
	
	// 검사할 상자 list
	@GetMapping("/CI_Search")
	public List<CrateLotDto> crateInspectSelectDao(SearchDto searchDto) {
		return processInspectionService.crateInspectSelectDao(searchDto);
	}
	
	// 검사된 상자 list
	@GetMapping("/PI_Search")
	public List<ProcessInspectDto> processInspectListDao(SearchDto searchDto) {
		return processInspectionService.processInspectListDao(searchDto);
	}
	
	// 검사 form 조회
	@GetMapping("/PIF_Search")
	public List<ProcessInspectDto> processInspectOneSelectDao(SearchDto searchDto) {
		return processInspectionService.processInspectOneSelectDao(searchDto);
	}
	
	@PostMapping("/PI_Save")
	public int processInspectInsert(ProcessInspectDto processInspectDto) {
		return processInspectionService.processInspectInsert(processInspectDto);
	}
	
	
}
