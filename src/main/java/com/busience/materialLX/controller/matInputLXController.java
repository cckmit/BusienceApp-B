package com.busience.materialLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matInputLXController {

	// MatInputLX
	@GetMapping("matInputLX")
	public String matInputLX(Model model) {
		model.addAttribute("pageName", "matInputLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matInputLX";
	}
	
}
