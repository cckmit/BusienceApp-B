package com.busience.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class popupController {

	//itemPopup
	@GetMapping("/itemPopup")
	public String itemPopup() {
		return "popup/itemPopup";
	}
	
	//machinePopup
	@GetMapping("/machinePopup")
	public String machinePopup() {
		return "popup/machinePopup";
	}
	
	//moldPopup
	@GetMapping("/moldPopup")
	public String moldPopup() {
		return "popup/moldPopup";
	}
	
	//defectPopup
	@GetMapping("/defectPopup")
	public String defectPopup() {
		return "popup/defectPopup";
	}
	
	//customerPopup
	@GetMapping("/customerPopup")
	public String customerPopup() {
	   return "popup/customerPopup";
	}
}