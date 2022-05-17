package com.busience.sales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.service.SalesLabelPrintService;
import com.busience.standard.dto.ItemDto;

@RestController("salesLabelPrintRestController")
@RequestMapping("salesLabelPrintRest")
public class salesLabelPrintRestController {

	@Autowired
	SalesLabelPrintService	salesLabelPrintService;

	//SI_Search
	@GetMapping("/SLP_Search")
	public List<ItemDto> SLP_Search() {
		return salesLabelPrintService.salesInputList();
	}
}
