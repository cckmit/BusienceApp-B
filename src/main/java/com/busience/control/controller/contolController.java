package com.busience.control.controller;

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
	public String workMonitoring(Model model) {
		return "workMonitoring";
	}
	
	//equipMonitoring
	@GetMapping("equipMonitoring")
	public String equipMonitoring(Model model) {
		return "equipMonitoring";
	}
	
	@GetMapping("Graphic2")
	public String Graphic2(Model model) {
		return "monitoring/Graphic/Graphic2";
	}
	
	@GetMapping("React1")
	public String React1(Model model) {
		return "React/React1";
	}
}
