package com.busience.tablet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.service.MaskInputService;

@RestController("maskInputRestController")
@RequestMapping("/tablet/maskInputRest")
public class maskInputRestController {

	@Autowired
	MaskInputService maskInputService;
		
	@GetMapping("/crateMachineSelect")
	public List<CrateLotDto> crateMachineSelect(SearchDto searchDto) {
		return maskInputService.crateMachineSelect(searchDto);
	}
	
	@GetMapping("/crateLotListSelect")
	public List<CrateLotDto> crateLotListSelect(SearchDto searchDto) {
		return maskInputService.crateLotListSelect(searchDto);
	}
	
	@GetMapping("/maskInputSelect")
	public List<WorkOrderDto> maskInputSelect() {
		return maskInputService.maskInputSelect();
	}
	
	@PostMapping("/maskInputUpdate")
	public int maskInputUpdate(CrateLotDto crateLotDto) {
		return maskInputService.maskInputUpdate(crateLotDto);
	}
	
	@GetMapping("/crateLotUpdate")
	public int crateLotUpdate(CrateLotDto crateLotDto) {
		return maskInputService.crateLotUpdate2(crateLotDto);
	}
	
	@GetMapping("/crateLotRefresh")
	public int crateLotRefresh(CrateLotDto crateLotDto) {
		return maskInputService.crateLotRefresh(crateLotDto);
	}
}
