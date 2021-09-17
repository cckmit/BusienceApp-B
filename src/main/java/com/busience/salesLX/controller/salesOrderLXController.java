package com.busience.salesLX.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class salesOrderLXController {

	// salesOrderMaster
	@GetMapping("salesOrderLX")
	public String salesOrder(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "수주 관리");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesOrderLX";
	}

	// salesOrderMaster
	@GetMapping("salesOrderLXPrint")
	public String salesPrint(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "수주 프린터");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesPrintLX";
	}
}
