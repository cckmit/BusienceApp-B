package com.busience.system.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.DtlDto;
import com.busience.system.dto.MenuMgmtDto;
import com.busience.system.service.MenuMgmtService;

@RestController("menuManageRestController")
@RequestMapping("menuManageRest")
public class menuManageRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MenuMgmtService menuMgmtService;
	
	@GetMapping("/MMSP_Search")
	public List<DtlDto> MMSP_Search(Principal principal) {
		return menuMgmtService.menuMgmtParentList(principal.getName());
	}

	@GetMapping("/MMS_Search")
	public List<MenuMgmtDto> view(String Menu_User_Code) {
		return menuMgmtService.menuMgmtList(Menu_User_Code);
	}
	
	// MM_Update
	@PostMapping("/MM_Update")
	public int MM_Update(@RequestBody List<MenuMgmtDto> jsonDataList) {
		return menuMgmtService.menuMgmtUpdate(jsonDataList);
	}
}
