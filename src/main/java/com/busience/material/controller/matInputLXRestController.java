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
	
	//matInputList
	@GetMapping("/MIL_Search")
	public List<InMatDto> MIL_Search(SearchDto searchDto) {
		return matInputService.matInputList(searchDto);
	}
	
	//matInputList
	@GetMapping("/MIOL_Search")
	public List<InMatDto> MIOL_Search(SearchDto searchDto) {
		return matInputService.matInputOtherList(searchDto);
	}
	
	//matInputDeliveryMaster
	@GetMapping("/MIDM_Search")
	public List<InMatDto> MIDM_Search(SearchDto searchDto) {
		return matInputService.matInputDeliveryMaster(searchDto);
	}
	
	//matInputDeliverySub
	@GetMapping("/MIDS_Search")
	public List<InMatDto> MIDS_Search(SearchDto searchDto) {
		return matInputService.matInputDeliverySub(searchDto);
	}
}
