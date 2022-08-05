package com.busience.system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.system.dto.UserMenuDto;
import com.busience.system.service.UserMenuService;

@RestController("userMenuManageRestController")
@RequestMapping("userMenuManageRest")
public class userMenuManageRestController {

	@Autowired
	UserMenuService userMenuService;
	
	//allMenuSearch
	@GetMapping("/allMenuSearch")
	public List<UserMenuDto> allMenuSearch(Principal principal) {
		return userMenuService.allMenuList(principal.getName());
	}
	
	//userMenuSearch
	@GetMapping("/userMenuSearch")
	public List<UserMenuDto> userMenuSearch(Principal principal) {
		return userMenuService.userMenuList(principal.getName());
	}
	
	//userMenuInsert
	@PostMapping("/userMenuInsert")
	public int userMenuInsert(UserMenuDto userMenuDto, Principal principal) {
		return userMenuService.userMenuInsert(userMenuDto, principal.getName());
	}
	
	//userMenuDelete
	@DeleteMapping("/userMenuDelete")
	public int userMenuDelete(UserMenuDto userMenuDto, Principal principal) {
		return userMenuService.userMenuDelete(userMenuDto, principal.getName());
	}
}
