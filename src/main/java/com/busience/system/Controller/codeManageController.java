package com.busience.system.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("codeManageController")
public class codeManageController {

	@GetMapping("/codeManage")
	public String codeManage() {
		return "system/codeManage";
	}
	
}
