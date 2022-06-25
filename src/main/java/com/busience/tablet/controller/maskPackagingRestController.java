package com.busience.tablet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.tablet.service.MaskPackagingService;

@RestController("maskPackagingRestController")
@RequestMapping("/tablet/maskPackagingRest")
public class maskPackagingRestController {

	@Autowired
	MaskPackagingService maskPackagingService;
	
	@GetMapping("/maskPackagingSelect")
	public List<WorkOrderDto> maskPackagingSelect() {
		return maskPackagingService.maskPackagingSelect();
	}
	
	@GetMapping("/packagingLineListSelect")
	public List<WorkOrderDto> packagingLineListSelect(SearchDto searchDto) {
		return maskPackagingService.packagingLineListSelect(searchDto);
	}
	
	@PostMapping("/smallPackagingSelect")
	public String smallPackagingSelect(SearchDto searchDto) {
		return maskPackagingService.smallPackagingSave(searchDto);
	}
	
	@PostMapping("/smallPackagingSave")
	public String smallPackagingSave(SearchDto searchDto) {
		return maskPackagingService.smallPackagingSave(searchDto);
	}
}
