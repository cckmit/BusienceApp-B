package com.busience.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("routingInputController")
public class routingInputController {
	
	@GetMapping("routingInput")
	public String routingInput(Model model) {
		model.addAttribute("pageName", "routingInput");
		model.addAttribute("user_name", "관리자");
		return "standard/routingInput";
	}
	
}
