package com.busience.meterialLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class matOrderLXContoller {
	
	@Autowired
	DataSource dataSource;

	//OrderMaster
	@GetMapping("matOrderLX")
	public String matOrderLX(Model model) {
		
		model.addAttribute("pageName", "matOrder");
		model.addAttribute("user_name", "관리자");
		
		return "materialLX/matOrderLX";
	}
	//matOrderPrint
	@RequestMapping(value = "matOrderPrint", method = RequestMethod.POST)
	public String orderPrint(Model model, HttpServletRequest request) throws SQLException {
		
		return "materialLX/matOrderLXPrint";
	}
	//matOrderPrint
	@RequestMapping(value = "matOrderPrintN", method = RequestMethod.POST)
	public String orderPrintN(Model model, HttpServletRequest request) throws SQLException {
		return "material/matOrderPrintN";
	}	
}
