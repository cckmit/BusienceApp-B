package com.busience.material.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.InMatInspectDto;
import com.busience.material.service.MatInputInspectionService;

@RestController("matInputInspectionRestController")
@RequestMapping("matInputInspectionRest")
public class matInputInspectionRestController {

	@Autowired
	MatInputInspectionService matInputInspectionService;
	
	@GetMapping("/MII_Search")
	public List<InMatDto> temporaryStorageSelectDao(SearchDto searchDto) {
		//System.out.println(searchDto);
		return matInputInspectionService.temporaryStorageSelectDao(searchDto);
	}
	
	@PostMapping("/MII_Save")
	public int InMatInspectInsertDao(HttpServletRequest request, InMatInspectDto inMatInspectDto) {
		String[] standard = request.getParameterValues("standard");
		String[] value1 = request.getParameterValues("value1");
		String[] value2 = request.getParameterValues("value2");
		String[] value3 = request.getParameterValues("value3");
		String[] value4 = request.getParameterValues("value4");
		String[] value5 = request.getParameterValues("value5");
		String[] stnd1 = request.getParameterValues("stnd1");
		String[] stnd2 = request.getParameterValues("stnd2");
		String[] status = request.getParameterValues("status");
		
		System.out.println("standard = " + standard);
		
		return matInputInspectionService.InMatInspectInsertDao(inMatInspectDto, standard, value1, value2, value3, value4,
				value5, stnd1, stnd2, status);
		
		
	}
}
