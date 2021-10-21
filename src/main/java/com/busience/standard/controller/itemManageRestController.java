package com.busience.standard.controller;

import java.security.Principal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping("/itemManageInsert")
	public String itemManageInsert(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {
		String modifier = principal.getName();

		product_INFO_TBL.setPRODUCT_MODIFIER(modifier);

		itemService.insertItemCode(product_INFO_TBL);
		
		return "Success";
	}

	// update
	@GetMapping("/itemManageUpdate")
	public String itemManageUpdate(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {
		
		String modifier = principal.getName();

		product_INFO_TBL.setPRODUCT_MODIFIER(modifier);

		itemService.updateItemCode(product_INFO_TBL);
		
		return "Success";
	}

	// delete
	@GetMapping("/itemManageDelete")
	public String itemManageDelete(PRODUCT_INFO_TBL product_INFO_TBL) {
		
		itemService.deleteItemCode(product_INFO_TBL.getPRODUCT_ITEM_CODE());
		
		return "Success";
	}
}
