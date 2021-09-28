package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proListLXController {

	@GetMapping("proItemListLX")
	public String proItemListLX(Model model,HttpServletRequest request) throws SQLException
	{
		model.addAttribute("pageName", "생산실적 이력관리(제품별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proItemListLX";
	}
	
	@GetMapping("proMachineListLX")
	public String proMachineListLX(Model model,HttpServletRequest request) throws SQLException
	{
		model.addAttribute("pageName", "생산실적 이력관리(설비별)");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proMachineListLX";
	}
}
