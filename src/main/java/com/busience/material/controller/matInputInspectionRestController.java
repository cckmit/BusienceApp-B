package com.busience.material.controller;

import java.security.Principal;
import java.util.Arrays;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public int InMatInspectInsertDao(HttpServletRequest request, InMatInspectDto inMatInspectDto, Principal principal) throws JsonMappingException, JsonProcessingException {
		String standard = request.getParameter("standard");
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String value3 = request.getParameter("value3");
		String value4 = request.getParameter("value4");
		String value5 = request.getParameter("value5");
		String stnd1 = request.getParameter("stnd1");
		String stnd2 = request.getParameter("stnd2");
		String status = request.getParameter("status");
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<InMatInspectDto> value1List = Arrays.asList(mapper.readValue(value1, InMatInspectDto[].class));
		List<InMatInspectDto> value2List = Arrays.asList(mapper.readValue(value2, InMatInspectDto[].class));
		List<InMatInspectDto> value3List = Arrays.asList(mapper.readValue(value3, InMatInspectDto[].class));
		List<InMatInspectDto> value4List = Arrays.asList(mapper.readValue(value4, InMatInspectDto[].class));
		List<InMatInspectDto> value5List = Arrays.asList(mapper.readValue(value5, InMatInspectDto[].class));
		List<InMatInspectDto> stnd1List = Arrays.asList(mapper.readValue(stnd1, InMatInspectDto[].class));
		List<InMatInspectDto> stnd2List = Arrays.asList(mapper.readValue(stnd2, InMatInspectDto[].class));
		List<InMatInspectDto> statusList = Arrays.asList(mapper.readValue(status, InMatInspectDto[].class));
		
		InMatInspectDto standardData = mapper.readValue(standard, InMatInspectDto.class);

		
		return matInputInspectionService.InMatInspectInsertDao(inMatInspectDto, standardData, value1List, value2List, value3List, value4List,
				value5List, stnd1List, stnd2List, statusList, principal.getName());
		
		
	}
}
