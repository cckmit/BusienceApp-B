package com.busience.standard.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("matmodelmappingController")
public class matmodelmappingController {
	
	@Autowired
	DataSource dataSource;

	@GetMapping("matmodelmapping")
	public String routingInput(Model model) {
		model.addAttribute("pageName", "matmodelmapping");
		model.addAttribute("user_name", "관리자");
		return "standard/matmodelmapping";
	}
	
}
