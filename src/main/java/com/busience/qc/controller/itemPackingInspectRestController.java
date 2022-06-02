package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.qc.service.ItemPackingInspectService;

@RestController("itemPackingInspectRestController")
@RequestMapping("itemPackingInspectRest")
public class itemPackingInspectRestController {

	@Autowired
	ItemPackingInspectService itemPackingInspectService;
	
	@GetMapping("/SIL_Search")
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto) {
		return itemPackingInspectService.salesItemListDao(searchDto);
	}
}
