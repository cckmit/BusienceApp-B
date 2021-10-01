package com.busience.control.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class workMonitoringController {

	@GetMapping("workMonitoring")
	public String list(Model model) throws UnknownHostException, ClassNotFoundException, SQLException
	{
		return "workMonitoring";
	}
	
}

