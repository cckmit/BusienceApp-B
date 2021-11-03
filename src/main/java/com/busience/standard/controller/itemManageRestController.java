package com.busience.standard.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.service.ItemService;

@RestController("itemManageRestController")
@RequestMapping("itemManageRest")
public class itemManageRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ItemService itemService;
	
	@GetMapping("/itemManageSelect")
	public List<PRODUCT_INFO_TBL> itemManageSelect() {
		return itemService.selectItemList();
	}

	// insert
	@PostMapping("/itemManageInsert")
	public int itemManageInsert(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {

		product_INFO_TBL.setPRODUCT_MODIFIER(principal.getName());

		return itemService.insertItemCode(product_INFO_TBL);
	}

	// update
	@PutMapping("/itemManageUpdate")
	public int itemManageUpdate(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {

		product_INFO_TBL.setPRODUCT_MODIFIER(principal.getName());

		return itemService.updateItemCode(product_INFO_TBL);
	}

	// delete
	@DeleteMapping("/itemManageDelete")
	public int itemManageDelete(PRODUCT_INFO_TBL product_INFO_TBL) {		
		return itemService.deleteItemCode(product_INFO_TBL.getPRODUCT_ITEM_CODE());
	}
}
