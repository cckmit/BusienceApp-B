package com.busience.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {
	
	@GetMapping("/typeAuthority")
	public String typeAuthority(Model model) {
		
		model.addAttribute("pageName", "권한 관리");
		return "system/typeAuthority";
	}
	
	@GetMapping("/menuManage")
	public String menuManage(Model model) {
		
		model.addAttribute("pageName", "메뉴 관리");
		return "system/menuManage";
	}
	
	@GetMapping("/codeManage")
	public String codeManage(Model model) {
		
		model.addAttribute("pageName", "공통코드 관리");
		return "system/codeManage";
	}
	
	@GetMapping("/userMenuManage")
	public String userMenuManage(Model model) {
		
		model.addAttribute("pageName", "사용자 메뉴 관리");
		return "system/userMenuManage";
	}
	
	@GetMapping("/menuAdd")
	public String menuAdd(Model model) {
		
		model.addAttribute("pageName", "메뉴 추가");
		return "system/menuAdd";
	}
}
