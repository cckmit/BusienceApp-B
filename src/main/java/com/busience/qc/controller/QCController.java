package com.busience.qc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class QCController {
	
	@Autowired
	DtlService dtlService;
	
	//oqcOutputInspect
	@GetMapping("/oqcOutputInspect")
	public String iqcInputInspect(Model model) {
		
		//로직
		int rogic = 22;
		model.addAttribute("rogicList", dtlService.getAlldtl(rogic));
		
		//작업자
		int in = 23;
		model.addAttribute("inList", dtlService.getAlldtl(in));
		
		//유형
		int cost = 25;
		model.addAttribute("costList", dtlService.getAlldtl(cost));
		
		//판정
		int check = 27;
		model.addAttribute("checkList", dtlService.getAlldtl(check));
		
		model.addAttribute("pageName", "출하 검사 관리");
		
		return "qc/oqcOutputInspect";
	}
	
	//oqcOutputList
	@GetMapping("oqcOutputList")
	public String oqcOutputList(Model model) {
		
		//로직
		int rogic = 22;
		model.addAttribute("rogicList", dtlService.getAlldtl(rogic));
		
		model.addAttribute("pageName", "출하 검사 실적");
		
		return "qc/oqcOutputList";
	}
	
	// oqcOutputDefecterate
	@GetMapping("oqcOutputDefecterate")
	public String oqcOutputDefecterate(Model model) {
		
		//로직
		int rogic = 22;
		model.addAttribute("rogicList", dtlService.getAlldtl(rogic));
		
		model.addAttribute("pageName", "제품별 불량율");
		
		return "qc/oqcOutputDefectrate/oqcMaster";
	}
	
	// defectInsert
	@GetMapping("defectInsert")
	public String defectInsert(Model model) {
		model.addAttribute("pageName", "불량 실적 입력");
		return "qc/defectInsert";
	}
	
	// defectInsertM
	@GetMapping("defectInsertM")
	public String defectInsertM(Model model) {
		return "normal/qc/defectInsertM";
	}
}
