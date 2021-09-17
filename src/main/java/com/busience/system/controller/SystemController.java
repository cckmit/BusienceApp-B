package com.busience.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemController {
	
	@RequestMapping("/{pageName}")
	public String pageName(@PathVariable String pageName, Model model) {
		
		model.addAttribute("pageName", pageName);
		model.addAttribute("user_name", "관리자");
		return "system/"+pageName;
	}
}
