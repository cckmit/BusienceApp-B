package com.busience.qc.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DTL_Service;

@Controller
public class QCController {
	private DTL_Service dtl_Service;
	
	public QCController(DTL_Service dtl_Service) {
		this.dtl_Service = dtl_Service;
	}
	
	//oqcOutputInspect
	@GetMapping("/oqcOutputInspect")
	public String iqcInputInspect(Model model) {
		
		//로직
		String rogic = Integer.toString(22);
		model.addAttribute("rogicList", dtl_Service.getAlldtl(rogic));
		
		//작업자
		String in = Integer.toString(23);
		model.addAttribute("inList", dtl_Service.getAlldtl(in));
		
		//유형
		String cost = Integer.toString(25);
		model.addAttribute("costList", dtl_Service.getAlldtl(cost));
		
		//판정
		String check = Integer.toString(27);
		model.addAttribute("checkList", dtl_Service.getAlldtl(check));
		
		model.addAttribute("pageName", "출하 검사 관리");
		
		return "qc/oqcOutputInspect";
	}
	
	//oqcOutputList
	@GetMapping("oqcOutputList")
	public String oqcOutputList(Model model) {
		
		//로직
		String rogic = Integer.toString(22);
		model.addAttribute("rogicList", dtl_Service.getAlldtl(rogic));
		
		model.addAttribute("pageName", "출하 검사 실적");
		
		return "qc/oqcOutputList";
	}
	
	// oqcOutputDefecterate
	@GetMapping("oqcOutputDefecterate")
	public String oqcOutputDefecterate(Model model) {
		
		//로직
		String rogic = Integer.toString(22);
		model.addAttribute("rogicList", dtl_Service.getAlldtl(rogic));
		
		model.addAttribute("pageName", "제품별 불량율");
		
		return "qc/oqcOutputDefectrate/oqcMaster";
	}
	
	// defectInsert
	@GetMapping("defectInsert")
	public String defectInsert(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		model.addAttribute("pageName", "불량 실적 입력");
		return "qc/defectInsert";
	}
	
	// defectInsertM
	@GetMapping("defectInsertM")
	public String defectInsertM(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		return "normal/qc/defectInsertM";
	}
}
