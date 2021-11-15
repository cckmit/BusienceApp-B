package com.busience.standard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.ItemDto;
import com.busience.standard.service.ItemService;

@RestController("itemManageRestController")
@RequestMapping("itemManageRest")
public class itemManageRestController {

	@Autowired
	ItemService itemService;
	
	@GetMapping("/itemManageSelect")
	public List<ItemDto> itemManageSelect() {
		return itemService.selectItemList();
	}

	// insert
	@PostMapping("/itemManageInsert")
	public int itemManageInsert(ItemDto itemDto) {
		return itemService.insertItemCode(itemDto);
	}

	// update
	@PutMapping("/itemManageUpdate")
	public int itemManageUpdate(ItemDto itemDto) {
		return itemService.updateItemCode(itemDto);
	}

	// delete
	@DeleteMapping("/itemManageDelete")
	public int itemManageDelete(ItemDto itemDto) {		
		return itemService.deleteItemCode(itemDto.getPRODUCT_ITEM_CODE());
	}
}
