package com.busience.material.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.RequestMasterDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.material.service.MatRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("matRequestRestController")
@RequestMapping("matRequestRest")
public class matRequestRestController {

	@Autowired
	MatRequestService matRequestService;
	
	@Autowired
	DataSource dataSource;
	
	@GetMapping("/MRM_Search")
	public List<RequestMasterDto> MRM_Search(SearchDto searchDto) {
		return matRequestService.RequestMasterSelect(searchDto);
	}
	
	@GetMapping("/MRS_Search")
	public List<RequestSubDto> MRS_Search(SearchDto searchDto) {
		return matRequestService.RequestSubSelect(searchDto);
	}
	
	//orderList delete
	@DeleteMapping("/MR_Delete")
	public int MR_Delete(@RequestBody List<RequestSubDto> requestSubDtoList) {
		return matRequestService.RequestDelete(requestSubDtoList);
	}
	
	@PostMapping("/MR_Save")
	public int MR_Save(@RequestParam("masterData") String masterData,
						@RequestParam("listData") String listData,
						Principal principal) {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			RequestMasterDto requestMasterDto = mapper.readValue(masterData, RequestMasterDto.class);
			
			List<RequestSubDto> requestSubDtoList = Arrays.asList(mapper.readValue(listData, RequestSubDto[].class));
			
			return matRequestService.RequestInsert(requestMasterDto, requestSubDtoList, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}