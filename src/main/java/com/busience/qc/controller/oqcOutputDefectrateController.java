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
public class oqcOutputDefectrateController {

	@Autowired
	DataSource dataSource;

	// matInReturn
	@GetMapping("oqcOutputDefecterate")
	public String orderMaster(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		model.addAttribute("rogicList", HomeRestController.dtl_tbl_select(dataSource, "22"));
		model.addAttribute("pageName", "제품별 불량율");
		model.addAttribute("user_name", "관리자");
		
		return "qc/oqcOutputDefectrate/oqcMaster";
	}
}
