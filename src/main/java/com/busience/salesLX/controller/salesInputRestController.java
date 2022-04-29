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

import com.busience.common.dto.SearchDto;
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
	
	// salesInput select
	@GetMapping("/SI_Search")
	public List<Sales_InMat_tbl> salesInMatSelectDao() {
		return salesInputService.salesInMatSelectDao();
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
			
			List<SalesPackingDto> salesPackingDtoList = Arrays.asList(mapper.readValue(packData, SalesPackingDto[].class));
			
			return salesInputService.salesInputInsert(sales_InMat_tbl, salesPackingDtoList, totalQty, principal.getName());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesInput List select
	@GetMapping("/SIL_Search")
	public List<Sales_InMat_tbl> salesInputListDao(Sales_InMat_tbl sales_InMat_tbl, SearchDto searchDto) {
		return salesInputService.salesInputListDao(sales_InMat_tbl, searchDto);
	}
	// salesInput Item View
	@GetMapping("/SIIL_Search")
	public List<Sales_InMat_tbl> salesInputItemViewDao(Sales_InMat_tbl sales_InMat_tbl, SearchDto searchDto) {
		return salesInputService.salesInputItemViewDao(sales_InMat_tbl, searchDto);
	}
	
}
