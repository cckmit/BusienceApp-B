package com.busience.meterialLX.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matInReturnLXController {

	@Autowired
	DataSource dataSource;

	// matInReturnLX
	@GetMapping("matInReturnLX")
	public String orderMaster(Model model) {
		model.addAttribute("pageName", "matInReturnLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matInReturnLX/matInReturnLXMaster";
	}

}
