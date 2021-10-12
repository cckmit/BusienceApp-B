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
public class defectInsertController {

	@Autowired
	DataSource dataSource;

	@GetMapping("defectInsert")
	public String defectInsert(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		model.addAttribute("pageName", "불량 실적 입력");
		return "qc/defectInsert";
	}
	
	@GetMapping("defectInsertM")
	public String defectInsertM(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		return "normal/qc/defectInsertM";
	}
	
}
