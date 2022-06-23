package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.qc.dto.ItemPackingInspectDto;
import com.busience.qc.service.ItemPackingInspectService;

@RestController("itemPackingInspectRestController")
@RequestMapping("itemPackingInspectRest")
public class itemPackingInspectRestController {

	@Autowired
	ItemPackingInspectService itemPackingInspectService;
	
	// 완제품 데이터 조회
	@GetMapping("/SIL_Search")
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto) {
		return itemPackingInspectService.salesItemListDao(searchDto);
	}
	
	// LotNo로 조회
	@GetMapping("/IP_Search")
	public List<ItemPackingInspectDto> itemPackingInspectListDao(SearchDto searchDto) {
		return itemPackingInspectService.itemPackingInspectListDao(searchDto);
	}
	
	// list 
	@GetMapping("/IPS_Search")
	public List<ItemPackingInspectDto> itemPackingInspectSearchDao(SearchDto searchDto) {
		return itemPackingInspectService.itemPackingInspectSearchDao(searchDto);
	}
	
	@PostMapping("/IPI_Save")
	public int itemPackingInspectInsertDao(@RequestBody List<ItemPackingInspectDto> dataList,  ItemPackingInspectDto itemPackingInspectDto) {
		return itemPackingInspectService.itemPackingInspectInsertDao(dataList, itemPackingInspectDto);
	}
}
