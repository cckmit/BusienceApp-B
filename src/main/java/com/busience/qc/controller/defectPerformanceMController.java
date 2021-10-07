package com.busience.qc.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class defectPerformanceMController {

	@Autowired
	DataSource dataSource;

	@GetMapping("defectPerformanceM")
	public String defectPerformanceM(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		return "normal/defectPerformanceM";
	}
	
}
