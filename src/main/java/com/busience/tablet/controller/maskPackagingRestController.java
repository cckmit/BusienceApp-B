package com.busience.tablet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;

@RestController("maskPackagingRestController")
@RequestMapping("/tablet/maskPackagingRest")
public class maskPackagingRestController {

	@GetMapping("/packagingInputSave")
	public String packagingInputSave(SearchDto searchDto) {
		return null;
	}	
}
