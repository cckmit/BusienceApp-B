package com.busience.wip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class wipController {

	@Autowired
	DtlService dtlService;
	
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
	
	// wipDefect
	@GetMapping("wipDefect")
	public String wipDefect(Model model) {
		model.addAttribute("pageName", "재공 불량 관리");
		return "wip/wipDefect";
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
	
	// wipDefectList
	@GetMapping("wipDefectList")
	public String wipDefectList(Model model) {
		model.addAttribute("pageName", "재공 불량 현황");
		return "wip/wipDefectList";
	}
	
	// wipInOutList
	@GetMapping("wipInOutList")
	public String wipInOutList(Model model) {
		//품목분류1
		int Item_Clsfc = 6;
		model.addAttribute("routingList", dtlService.getDtl(Item_Clsfc));
		model.addAttribute("pageName", "재공 입출고 현황");
		return "wip/wipInOutList";
	}
	
	// wipInOutList
	@GetMapping("wipProcessingList")
	public String wipProcessingList(Model model) {
		//품목분류1
		int Item_Clsfc = 6;
		model.addAttribute("routingList", dtlService.getDtl(Item_Clsfc));
		model.addAttribute("pageName", "재공 공정 현황");
		return "wip/wipProcessingList";
	}
}
