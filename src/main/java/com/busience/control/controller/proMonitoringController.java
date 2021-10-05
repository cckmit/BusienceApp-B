package com.busience.control.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proMonitoringController {

	@GetMapping("proMonitoring")
	public String proMonitoring(Model model) {
		model.addAttribute("pageName", "생산현황 모니터링");
		return "monitoring/proMonitoring";
	}
}
