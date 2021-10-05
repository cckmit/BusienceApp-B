package com.busience.salesLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class salesReportLXController {

	@Autowired
	DataSource dataSource;


	// salesOrderList
	@GetMapping("salesOrderListLX")
	public String salesOrderList(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "수주 조회");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesOrderList";
	}

	// fgoodsInoutList
	@GetMapping("salesInoutListLX")
	public String salesInoutList(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "제품 입출고 조회");
		model.addAttribute("user_name", "관리자");
		return "salesLX/salesInoutListLX";
	}

	// fgoodsStockMaster
	@GetMapping("salesStockLXMaster")
	public String salesStockMaster(Model model, HttpServletRequest request) throws SQLException {
		
		model.addAttribute("pageName", "제품 재고 현황");
		model.addAttribute("user_name", "관리자");
		return "salesLX/salesStock/salesStockMaster";
	}
}
