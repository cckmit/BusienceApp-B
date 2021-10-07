package com.busience.control.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("workOrderOIContoller")
public class workOrderOIContoller {

	@GetMapping("workorderoi")
	public String orderMaster(Model model, HttpServletRequest request) throws SQLException {
		return "normal/workorderoi";
	}
	
}
