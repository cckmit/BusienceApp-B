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
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.service.MatOutputService;

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
	@PostMapping("/matOutputSave")
	public int matOutputSave(@RequestBody List<OutMatDto> outMatDtoList, Principal principal) {
		return matOutputService.outMatInsert(outMatDtoList, principal.getName());
	}
	
	// orderList save
	@PostMapping("/matOutputLXSave")
	public int matOutputLXSave(@RequestBody List<OutMatDto> outMatDtoList, Principal principal) {
		return matOutputService.outMatLXInsert(outMatDtoList, principal.getName());
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
