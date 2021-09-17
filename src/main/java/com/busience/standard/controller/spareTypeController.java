package com.busience.standard.controller;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class spareTypeController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("spareTypeManage")
	public String list(Model model) throws SQLException {
		model.addAttribute("pageName", "spareTypeManage");
		model.addAttribute("user_name", "관리자");
		return "standard/spareTypeManage";
	}
}
