package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class workOrderListController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("workorderList")
	public String orderMaster(Model model, HttpServletRequest request) throws SQLException {
		
		model.addAttribute("pageName", "작업지시 조회");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/workorderList";
	}
}
