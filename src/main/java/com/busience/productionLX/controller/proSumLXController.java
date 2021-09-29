package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proSumLXController {

	@GetMapping("proItemSumLX")
	public String proItemSumLX(Model model, HttpServletRequest request) throws SQLException {
		
		model.addAttribute("pageName", "생산실적 관리(제품별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proItemSumLX";
	}

	@GetMapping("proMachineSumLX")
	public String proMachineSumLX(Model model, HttpServletRequest request) throws SQLException {
		
		model.addAttribute("pageName", "생산실적 관리(설비별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proMachineSumLX";
	}
	
	@GetMapping("mold1")
	public String list6(Model model, HttpServletRequest request) throws SQLException {
		return "productionLX/pro_mold_sum";
	}
	
	@GetMapping("proSumMonth")
	public String proSumMonth(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proSumMonth";
	}
	
	@GetMapping("proSumYear")
	public String proSumYear(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proSumYear";
	}
}

