package com.busience.system.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("usermenuManageController")
public class usermenuManageController {

	@GetMapping("/usermenuManage")
	public String usermenuManage() {
		return "system/usermenuManage";
	}
}
