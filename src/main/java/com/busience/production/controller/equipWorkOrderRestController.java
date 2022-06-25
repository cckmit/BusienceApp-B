package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.service.EquipWorkOrderService;

@RestController("equipWorkOrderRestController")
@RequestMapping("equipWorkOrderRest")
public class equipWorkOrderRestController {

	@Autowired
	EquipWorkOrderService equipWorkOrderService;
	
	@GetMapping("/equipOrderList")
	public List<EquipWorkOrderDto> equipWorkOrderSelectDao(SearchDto searchDto) {
		return equipWorkOrderService.equipWorkOrderSelect(searchDto);
	}
	
	@PostMapping("/equipOrderUpdate")
	public int equipWorkOrderUpdate(@RequestBody List<EquipWorkOrderDto> workOrderDtoList) {
		return equipWorkOrderService.equipWorkOrderUpdate(workOrderDtoList);
	}
}
