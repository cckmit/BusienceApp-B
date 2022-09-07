package com.busience.material.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.service.MatInReturnService;

@RestController("matInReturnRestController")
@RequestMapping("matInReturnRest")
public class matInReturnRestController {

	@Autowired
	MatInReturnService matInReturnService;
	
	//MIRI_Search
	@GetMapping("/MIRI_Search")
	public List<InMatDto> MIRI_Search(SearchDto searchDto) {
	 return matInReturnService.matInReturnSelect(searchDto);
	}
	
	@GetMapping("/matInReturnLXSelect")
	public List<InMatDto> matInReturnLXSelect(SearchDto searchDto) {
	 return matInReturnService.matInReturnLXSelect(searchDto);
	}
	
	//MIRI_Save
	@PostMapping("/MIRI_Save")
	public int MIRI_Save(@RequestBody List<InMatDto> inMatDtoList, Principal principal) {
		return matInReturnService.matInReturnSave(inMatDtoList, principal.getName());
	}
	
	//MIRI_Save
	@PostMapping("/matInReturnLXSave")
	public int matInReturnLXSave(@RequestBody List<InMatDto> inMatDtoList, Principal principal) {
		return matInReturnService.matInReturnLXSave(inMatDtoList, principal.getName());
	}
}
