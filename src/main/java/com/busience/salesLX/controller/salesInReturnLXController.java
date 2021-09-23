package com.busience.salesLX.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class salesInReturnLXController {

	@Autowired
	DataSource dataSource;

	// salesInReturn
	@GetMapping("salesInReturnLX")
	public String salesInReturn(Model model) {
		
		model.addAttribute("pageName", "입고 반품 관리");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesInReturnLX/salesInReturnLXMaster";
	}
}
