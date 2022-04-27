package com.busience.salesLX.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;
import com.busience.salesLX.service.SalesInputService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesInputRestController")
@RequestMapping("salesInputRest")
public class salesInputRestController {

	@Autowired
	SalesInputService salesInputService;
	
	// LotMaster select
	@GetMapping("/SIM_Search")
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto) {
		return salesInputService.salesInputLotMasterSelectDao(lotMasterDto);
	}
	
	// salesInput insert
	@PostMapping("/SI_Save")
	public int salesInputInsert(@RequestParam("salesinmatData") String salesinmatData, @RequestParam("packData") String packData, @RequestParam("totalQty") int totalQty, Principal principal) {
		System.out.println("salesinmatData = " + salesinmatData);
		System.out.println("selectData = " + packData);
		System.out.println("totalQty = " + totalQty);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			Sales_InMat_tbl sales_InMat_tbl = mapper.readValue(salesinmatData, Sales_InMat_tbl.class);
			
			List<SalesPackingDto> SalesPackingDtoList = Arrays.asList(mapper.readValue(packData, SalesPackingDto[].class));
			
			return salesInputService.salesInputInsert(sales_InMat_tbl, SalesPackingDtoList, totalQty, principal.getName());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
	}
}
