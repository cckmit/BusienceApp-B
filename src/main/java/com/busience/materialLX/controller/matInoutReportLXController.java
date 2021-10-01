package com.busience.materialLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matInoutReportLXController {

	// matInoutListLX
	@GetMapping("matInoutListLX")
	public String matInoutListLX(Model model) {
		model.addAttribute("pageName", "matInoutListLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matInoutListLX";
	}
}
