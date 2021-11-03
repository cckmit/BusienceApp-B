package com.busience.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class contolController {
	
	//proMonitoring
	@GetMapping("proMonitoring")
	public String proMonitoring(Model model) {
		model.addAttribute("pageName", "생산현황 모니터링");
		return "monitoring/proMonitoring";
	}
	
	//defectMonitoring
	@GetMapping("defectMonitoring")
	public String defectMonitoring(Model model) {
		model.addAttribute("pageName", "불량현황 모니터링");
		return "monitoring/defectMonitoring";
	}
	
	//workMonitoring
	@GetMapping("workMonitoring")
	public String workMonitoring() {
		return "normal/monitoring/workMonitoring";
	}
	
	//equipMonitoring
	@GetMapping("equipMonitoring")
	public String equipMonitoring() {
		return "normal/monitoring/equipMonitoring";
	}
	
	@GetMapping("workorderoi")
	public String orderMaster() {
		return "normal/monitoring/workorderoi";
	}
	
}
