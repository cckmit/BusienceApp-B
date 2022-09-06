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
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockDto;
import com.busience.material.dto.TransDto;
import com.busience.material.service.MatOutReturnService;
import com.busience.material.service.StockService;

@RestController("matOutReturnRestController")
@RequestMapping("matOutReturnRest")
public class matOutReturnRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatOutReturnService matOutReturnService;
	
	@Autowired	
	StockService stockService;
	
	@GetMapping("/stockTransSelect")
	public List<LotMasterDto> StockTransSelect(SearchDto searchDto){
		return matOutReturnService.stockTransSelect(searchDto);
	}
	
	@GetMapping("/outReturnLXSearch")
	public List<StockDto> outReturnLXSearch(SearchDto searchDto) {
		return stockService.stockSelect(searchDto);
	}
	
	//MORI_Save
	@PostMapping("/outReturnLXSave")
	public int outReturnLXSave(@RequestBody List<StockDto> StockDtoList, Principal principal) {
		return matOutReturnService.matOutReturnLXSave(StockDtoList, principal.getName());
	}
	
	//MORI_Search
	@GetMapping("/outReturnSearch")
	public List<OutMatDto> outReturnSearch(SearchDto searchDto) {
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
