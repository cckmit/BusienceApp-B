package com.busience.material.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.InMatDto;
import com.busience.material.service.MatInputService;

@RestController("matInputLXRestController")
@RequestMapping("matInputLXRest")
public class matInputLXRestController {

	@Autowired
	MatInputService matInputService;
		
	// MIM_Save
	@PostMapping("/MIM_Save")
	public int MIM_Save(@RequestBody List<InMatDto> InMatDtoList, Principal principal) {
		return matInputService.matInputRegister(InMatDtoList, principal.getName());
	}
}
