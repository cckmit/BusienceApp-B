package com.busience.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class popupController {

	//itemPopup
	@GetMapping("/itemPopup")
	public String itemPopup() {
		return "normal/popup/itemPopup";
	}
	
	//machinePopup
	@GetMapping("/machinePopup")
	public String machinePopup() {
		return "normal/popup/machinePopup";
	}
	
	//moldPopup
	@GetMapping("/moldPopup")
	public String moldPopup() {
		return "normal/popup/moldPopup";
	}
	
	//defectPopup
	@GetMapping("/defectPopup")
	public String defectPopup() {
		return "normal/popup/defectPopup";
	}
	
	//customerPopup
	@GetMapping("/customerPopup")
	public String customerPopup() {
	   return "normal/popup/customerPopup";
	}
}