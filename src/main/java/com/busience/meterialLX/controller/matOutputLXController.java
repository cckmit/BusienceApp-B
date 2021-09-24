package com.busience.meterialLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matOutputLXController {

	// MatOutputLX
	@GetMapping("matOutputLX")
	public String matOutputLX(Model model) {
		model.addAttribute("pageName", "matOutputLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matOutputLX";
	}

}
