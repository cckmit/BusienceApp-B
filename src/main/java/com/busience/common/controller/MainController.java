package com.busience.common.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping("/")
	public String login() {
		return "thymeleaf/login";
	}
		
	@GetMapping("/main")
	public String main(Model model, Principal principal) {
		
		model.addAttribute("pageName", "메인");
		return "main";
	}
	
	@GetMapping("/pwchange")
	public String pwchange() {
		return "thymeleaf/pwchange";
	}
}