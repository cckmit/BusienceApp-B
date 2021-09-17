package com.busience.salesLX.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class salesOutputLXController {

	// salesOutputLX
	@GetMapping("salesOutputLX")
	public String salesOutputLX(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "출고 관리");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesOutputLX";
	}
}
