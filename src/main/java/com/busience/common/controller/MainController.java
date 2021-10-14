package com.busience.common.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping("/")
	public String login() {
		return "thymeleaf/login";
	}
		
	@GetMapping("/main")
	public String main(Model model, Principal principal) throws ClassNotFoundException, SQLException {
		model.addAttribute("pageName", "메인");
		
		return "main";
	}
	
	@GetMapping("/pwchange")
	public String pwchange() {
		return "thymeleaf/pwchange";
	}
}