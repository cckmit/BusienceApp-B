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
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.TransDto;
import com.busience.material.service.MatOutReturnService;

@RestController("matOutReturnRestController")
@RequestMapping("matOutReturnRest")
public class matOutReturnRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatOutReturnService matOutReturnService;
	
	@GetMapping("/stockTransSelect")
	public List<LotMasterDto> StockTransSelect(SearchDto searchDto){
		return matOutReturnService.StockTransSelect(searchDto);
	}
	
	//MORI_Search
	@GetMapping("/MORI_Search")
	public List<LotMasterDto> MORI_Search(SearchDto searchDto) {
		return matOutReturnService.matOutReturnSelect(searchDto);
	}
	
	//MORI_Save
	@PostMapping("/MORI_Save")
	public int MORI_Save(@RequestBody List<LotMasterDto> lotMasterDtoList, Principal principal) {
		return matOutReturnService.matOutReturnSave(lotMasterDtoList, principal.getName());
	}
	
	//MORI_Search
	@GetMapping("/MORS_Search")
	public List<TransDto> MORS_Search(SearchDto searchDto) {
		return matOutReturnService.transSelect(searchDto);
	}
}
