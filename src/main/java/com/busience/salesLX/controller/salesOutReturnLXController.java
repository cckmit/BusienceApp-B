package com.busience.salesLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class salesOutReturnLXController {

	// fgoodsOutReturn
	@GetMapping("salesOutReturnLX")
	public String salesOutReturn(Model model) {

		model.addAttribute("pageName", "판매 반품 관리");
		model.addAttribute("user_name", "관리자");

		return "salesLX/salesOutReturnLX/salesOutReturnLXMaster";
	}
}
