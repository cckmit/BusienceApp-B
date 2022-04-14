package com.busience.material.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.service.MatOutReturnService;

@RestController("matOutReturnLXRestController")
@RequestMapping("matOutReturnLXRest")
public class matOutReturnLXRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatOutReturnService matOutReturnService;
	
	//MIRI_Search
	@GetMapping("/MORI_Search")
	public List<OutMatDto> MORI_Search(SearchDto searchDto) {
		return matOutReturnService.matOutReturnSelect(searchDto);
	}
	
	//MIRI_Save
	@PostMapping("/MORI_Save")
	public int MORI_Save(@RequestBody List<OutMatDto> outMatDtoList, Principal principal) {
		return matOutReturnService.matOutReturnSave(outMatDtoList, principal.getName());
	}
}
