package com.busience.meterialLX.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matOutputLXController {

	@Autowired
	DataSource dataSource;

	// MatOutputLX
	@GetMapping("matOutputLX")
	public String matOutputLX(Model model) {
		model.addAttribute("pageName", "matOutputLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matOutputLX";
	}

}
