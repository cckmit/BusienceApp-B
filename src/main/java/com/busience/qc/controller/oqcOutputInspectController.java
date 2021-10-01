package com.busience.qc.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.controller.HomeRestController;

@Controller
public class oqcOutputInspectController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("/oqcOutputInspect")
	public String iqcInputInspect(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException
	{
		model.addAttribute("rogicList", HomeRestController.dtl_tbl_select(dataSource,"22"));
		model.addAttribute("inList", HomeRestController.dtl_tbl_select(dataSource,"23"));
		model.addAttribute("costList", HomeRestController.dtl_tbl_select(dataSource,"25"));
		model.addAttribute("checkList", HomeRestController.dtl_tbl_select(dataSource,"27"));
		model.addAttribute("pageName", "출하 검사 관리");
		model.addAttribute("user_name", "관리자");
		
		return "qc/oqcOutputInspect";
	}
}
