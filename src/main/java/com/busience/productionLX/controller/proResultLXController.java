package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proResultLXController {

	@GetMapping("proResultLX")
	public String proResultLX(Model model, HttpServletRequest request) throws SQLException {

		model.addAttribute("pageName", "생산 실적 관리");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/proResult";
	}
	
}
