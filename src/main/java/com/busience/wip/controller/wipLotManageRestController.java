package com.busience.wip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dto.ItemDto;
import com.busience.wip.dto.WipLotTransDto;
import com.busience.wip.service.WipLotManageService;

@RestController("wipLotManageRestController")
@RequestMapping("wipLotManageRest")
public class wipLotManageRestController {

	@Autowired
	WipLotManageService wipLotManageService; 
	

	@GetMapping("/wipLotManageSelect")
	public List<WipLotTransDto> wipLotManageSelect(SearchDto searchDto) {
		return wipLotManageService.wipLotManageList(searchDto);
	}
	
	@GetMapping("/wipLastDataSelect")
	public List<WipLotTransDto> wipLastDataSelect(SearchDto searchDto) {
		return wipLotManageService.wipLastData(searchDto);
	}
	
	@GetMapping("/wipLotManageSubSelect")
	public List<ItemDto> wipLotManageSubSelect() {
		return wipLotManageService.selectItemList();
	}
	
	@GetMapping("/wipLabelPrint")
	public WipLotTransDto wipLabelPrint(ItemDto itemDto) {
		return wipLotManageService.wipLabelPrint(itemDto);
	}
	
	@GetMapping("/wipInOutListSelect")
	public List<WipLotTransDto> wipInOutListSelect() {

		return wipLotManageService.wipInOutList();
	}
	
	@GetMapping("/wipInputListSelect")
	public List<WipLotTransDto> wipInputListSelect(SearchDto searchDto) {
		return wipLotManageService.wipInputList(searchDto);
	}
	
	@GetMapping("/wipOutputListSelect")
	public List<WipLotTransDto> wipOutputListSelect(SearchDto searchDto) {
		return wipLotManageService.wipOutputList(searchDto);
	}
	
	@PostMapping("/wipInOutInsert")
	public String wipInOutInsert(String wip_LotNo) {
		return wipLotManageService.wipInOutInsert(wip_LotNo);
	}
	
	@GetMapping("/wipLotMasterListSelect")
	public List<WipLotTransDto> wipLotMasterListSelect(WipLotTransDto wipLotTransDto) {
		return wipLotManageService.wipLotMasterList(wipLotTransDto);
	}
	
	@GetMapping("/wipProcessingListSelect")
	public List<WipLotTransDto> wipProcessingListSelect(WipLotTransDto wipLotTransDto) {
		return wipLotManageService.wipProcessingList(wipLotTransDto);
	}
		
	@PostMapping("/wipInputRollback")
	public int wipInputRollback(WipLotTransDto wipLotTransDto) {

		return wipLotManageService.wipInputRollback(wipLotTransDto);
	}
	
	@PostMapping("/wipOutputRollback")
	public int wipOutputRollback(WipLotTransDto wipLotTransDto) {

		return wipLotManageService.wipOutputRollback(wipLotTransDto);
	}
}
