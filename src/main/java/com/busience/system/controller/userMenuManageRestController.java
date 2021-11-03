package com.busience.system.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.system.dto.UserMenuDto;
import com.busience.system.service.UserMenuService;

@RestController("userMenuManageRestController")
@RequestMapping("userMenuManageRest")
public class userMenuManageRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	UserMenuService userMenuService;
	
	//userMenuSearch
	@GetMapping("/userMenuSearch")
	public List<UserMenuDto> userMenuSearch(Principal principal) {
		return userMenuService.userMenuList(principal.getName());
	}
	
	//userMenuInsert
	@PostMapping("/userMenuInsert")
	public int userMenuInsert(@RequestBody List<UserMenuDto> UserMenuDtoList, Principal principal) {
		return userMenuService.userMenuInsert(UserMenuDtoList, principal.getName());
	}
	
	//userMenuDelete
	@DeleteMapping("/userMenuDelete")
	public int userMenuDelete(@RequestBody List<UserMenuDto> UserMenuDtoList) {
		return userMenuService.userMenuDelete(UserMenuDtoList);
	}
}
