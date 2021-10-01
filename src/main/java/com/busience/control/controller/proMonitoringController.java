package com.busience.control.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proMonitoringController {

	@GetMapping("proMonitoring")
	public String list(Model model) throws UnknownHostException, ClassNotFoundException, SQLException
	{
		return "monitoring/proMonitoring";
	}
}
