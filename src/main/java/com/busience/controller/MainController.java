package com.busience.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String login() {
		return "thymeleaf/login";
	}
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
}