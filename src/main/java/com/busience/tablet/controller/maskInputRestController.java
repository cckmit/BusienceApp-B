package com.busience.tablet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.service.EquipWorkOrderService;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.service.MaskInputService;

@RestController("maskInputRestController")
@RequestMapping("/tablet/maskInputRest")
public class maskInputRestController {

	@Autowired
	MaskInputService maskInputService;
		
	@Autowired
	EquipWorkOrderService equipWorkOrderService;
	
	@GetMapping("/crateLotListSelect")
	public List<CrateLotDto> crateLotListSelect(SearchDto searchDto) {
		return maskInputService.crateLotListSelect(searchDto);
	}
	
	@GetMapping("/maskInputSelect")
	public List<EquipWorkOrderDto> maskInputSelect(SearchDto searchDto) {
		return equipWorkOrderService.equipWorkOrderSelect(searchDto);
	}
	
	@PostMapping("/maskInputUpdate")
	public int maskInputUpdate(CrateLotDto crateLotDto) {
		return maskInputService.maskInputUpdate(crateLotDto);
	}
}
