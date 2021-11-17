package com.busience.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping("/")
	public String login() {
		return "thymeleaf/login";
	}
		
	@GetMapping("/main")
	public String main(Model model) {
		model.addAttribute("pageName", "메인");		
		return "main";
	}
	
	@GetMapping("/pwChange")
	public String pwchange() {
		return "thymeleaf/pwChange";
	}
}