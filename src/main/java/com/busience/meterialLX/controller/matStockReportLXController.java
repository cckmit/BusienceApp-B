package com.busience.meterialLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class matStockReportLXController {

	// matStockMaster
	@GetMapping("matStockMasterLX")
	public String matStockMasterLX(Model model) {
		model.addAttribute("pageName", "matStockMasterLX");
		model.addAttribute("user_name", "관리자");
		return "materialLX/matStockLX/matStockMasterLX";
	}
}
