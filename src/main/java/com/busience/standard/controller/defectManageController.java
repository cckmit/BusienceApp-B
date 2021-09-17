package com.busience.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class defectManageController {

	@GetMapping("defectManage")
	public String defect(Model model){
		model.addAttribute("pageName", "defectManage");
		model.addAttribute("user_name", "관리자");
		return "standard/defectManage";
	}
	
}
