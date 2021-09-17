package com.busience.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class packagingManageController {

	@GetMapping("packagingManage")
	public String packagingManage(Model model) {
		model.addAttribute("pageName", "packagingManage");
		model.addAttribute("user_name", "관리자");
		return "standard/packagingManage";
	}
}
