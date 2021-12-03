package com.busience.salesLX.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_InMat_tbl;
import com.busience.salesLX.service.SalesInputLXService;
import com.busience.standard.dto.ItemDto;

@RestController("salesInputLXRestController")
@RequestMapping("salesInputLXRest")
public class salesInputLXRestController {

	@Autowired
	SalesInputLXService salesInputLXService;

	//SI_Search
	@GetMapping("/SI_Search")
	public List<ItemDto> SI_Search() {
		return salesInputLXService.salesInputList();
	}
	
	// SI_Save
	@PostMapping("/SI_Save")
	public int SI_Save(@RequestBody List<Sales_InMat_tbl> sales_InMat_tbl_List, Principal principal) {
		return salesInputLXService.salesInputRegister(sales_InMat_tbl_List, principal.getName());
	}
}
