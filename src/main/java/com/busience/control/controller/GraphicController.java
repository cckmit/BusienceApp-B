package com.busience.control.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphicController {

	@GetMapping("equipMonitoring")
	public String Graphic1(Model model)
	{
		return "monitoring/Graphic/Graphic1";
	}
	
	@GetMapping("Graphic2")
	public String Graphic2(Model model)
	{
		return "monitoring/Graphic/Graphic2";
	}
	
	@GetMapping("React1")
	public String React1(Model model)
	{
		return "React/React1";
	}
	
}
