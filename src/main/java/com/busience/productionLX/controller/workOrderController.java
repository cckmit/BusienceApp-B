package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class workOrderController {

	
	@GetMapping("workorder")
	public String orderMaster(Model model, HttpServletRequest request) throws SQLException {
		
		model.addAttribute("pageName", "작업 지시");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/workorder";
	}
	
	@GetMapping("workorder_auto_data_send")
	public String workorder_auto_data_send(Model model, HttpServletRequest request) throws SQLException {
		return "productionLX/workorder_auto_data_send";
	}
}
