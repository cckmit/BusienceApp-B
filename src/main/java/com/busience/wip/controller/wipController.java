package com.busience.wip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class wipController {

	// wipLotManage
	@GetMapping("wipLotManage")
	public String wipLotManage(Model model) {
		model.addAttribute("pageName", "재공 LOT 관리");
		return "wip/wipLotManage";
	}
	
	// wipInOut
	@GetMapping("wipInOut")
	public String wipInput(Model model) {
		model.addAttribute("pageName", "재공 입출고 관리");
		return "wip/wipInOut";
	}
	
	// wipInputList
	@GetMapping("wipInputList")
	public String wipInputList(Model model) {
		model.addAttribute("pageName", "재공 입고 현황");
		return "wip/wipInputList";
	}
	
	// wipInputList
	@GetMapping("wipOutputList")
	public String wipOutputList(Model model) {
		model.addAttribute("pageName", "재공 출고 현황");
		return "wip/wipOutputList";
	}
	
	// wipInOutList
	@GetMapping("wipInOutList")
	public String wipInOutList(Model model) {
		model.addAttribute("pageName", "재공 입출고 현황");
		return "wip/wipInOutList";
	}
	
	// wipInOutList
	@GetMapping("wipProcessingList")
	public String wipProcessingList(Model model) {
		model.addAttribute("pageName", "재공 공정 현황");
		return "wip/wipProcessingList";
	}
}
