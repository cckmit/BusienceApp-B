package com.busience.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.system.dto.MenuMgmtDto;
import com.busience.system.service.MenuMgmtService;

@RestController("menuManageRestController")
@RequestMapping("menuManageRest")
public class menuManageRestController {

	@Autowired
	MenuMgmtService menuMgmtService;
	
	@GetMapping("/MMS_Search")
	public List<MenuMgmtDto> MMS_Search(String Menu_User_Code) {
		return menuMgmtService.menuMgmtList(Menu_User_Code);
	}
	
	// MM_Update
	@PutMapping("/MM_Update")
	public int MM_Update(@RequestBody List<MenuMgmtDto> menuMgmtDtoList) {
		return menuMgmtService.menuMgmtUpdate(menuMgmtDtoList);
	}
}
