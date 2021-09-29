package com.busience.meterialLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matOrderLXContoller {
	
	//OrderMaster
	@GetMapping("matOrderLX")
	public String matOrderLX(Model model) {
		
		model.addAttribute("pageName", "matOrder");
		model.addAttribute("user_name", "관리자");
		
		return "materialLX/matOrderLX";
	}
}
