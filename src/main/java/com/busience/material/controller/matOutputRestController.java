package com.busience.material.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.material.service.MatOutputService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("matOutputRestController")
@RequestMapping("matOutputRest")
public class matOutputRestController {

	@Autowired
	MatOutputService matOutputService;
	
	@GetMapping("/LM_Search")
	public List<LotMasterDto> LM_Search(SearchDto searchDto) {
		searchDto.setWarehouse("50");
		return matOutputService.LotMasterSelect(searchDto);
	}
	
	// orderList save
	@PostMapping("/MOM_Save")
	public int MOM_Save(@RequestParam("masterData") String masterData,
						@RequestParam("subData") String subData,
						Principal principal) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			RequestSubDto requestSubDto = mapper.readValue(masterData, RequestSubDto.class);
			
			List<OutMatDto> OutMatDtoList = Arrays.asList(mapper.readValue(subData, OutMatDto[].class));
			
			return matOutputService.outMatInsert(requestSubDto, OutMatDtoList, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@GetMapping("/MOL_Search")
	public List<OutMatDto> MOL_Search(SearchDto searchDto){
		return matOutputService.matOutputList(searchDto);
	}
	
	@GetMapping("/MOOL_Search")
	public List<OutMatDto> MOOL_Search(SearchDto searchDto){
		return matOutputService.matOutputOtherList(searchDto);
	}
	
	@GetMapping("/MODM_Search")
	public List<OutMatDto> MODM_Search(SearchDto searchDto){
		return matOutputService.matOutputDeliveryMaster(searchDto);
	}
	
	@GetMapping("/MODS_Search")
	public List<OutMatDto> MODS_Search(SearchDto searchDto){
		return matOutputService.matOutputDeliverySub(searchDto);
	}
}
