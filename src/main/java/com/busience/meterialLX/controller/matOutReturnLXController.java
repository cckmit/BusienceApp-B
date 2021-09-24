package com.busience.meterialLX.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matOutReturnLXController {

	@Autowired
	DataSource dataSource;
	
	//matInReturn
	@GetMapping("matOutReturnLX")
	public String matOutReturnLX(Model model) {
		model.addAttribute("pageName", "matOutReturnLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matOutReturnLX/matOutReturnLXMaster";
	}
}
