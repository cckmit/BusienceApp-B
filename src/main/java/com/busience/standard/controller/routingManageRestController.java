package com.busience.standard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.RoutingDto;
import com.busience.standard.service.RoutingService;

@RestController("routingManageRestController")
@RequestMapping("routingManageRest")
public class routingManageRestController {

	@Autowired
	RoutingService routingService;
	
	@GetMapping("/routingManageSelect")
	public List<RoutingDto> routingManageSelect() {
		return routingService.selectRoutingList();
	}
	
	@GetMapping("/routingManageDetail")
	public List<RoutingDto> routingManageDetail(RoutingDto routingDto) {
		return routingService.selectRoutingDetail(routingDto);
	}
	
	@PostMapping("/routingManageUpdate")
	public int routingManageUpdate(@RequestBody List<RoutingDto> routingDtoList) {
		return routingService.updateRouting(routingDtoList);
	}
}
