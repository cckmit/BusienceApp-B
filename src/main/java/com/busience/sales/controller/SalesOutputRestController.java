package com.busience.sales.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.sales.dto.SalesOutMatDto;
import com.busience.sales.dto.SalesOutputOrderMasterDto;
import com.busience.sales.dto.Sales_OutMat_tbl;
import com.busience.sales.service.SalesOutputService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("SalesOutputRestController")
@RequestMapping("salesOutputRest")
public class SalesOutputRestController {

	@Autowired
	SalesOutputService salesOutputService;

	// 영업 LotMaster 조회
	@GetMapping("/SLM_Search")
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto) {
		return salesOutputService.salesOutputLotMasterDao(searchDto);
	}

	// 영업 LotMaster 조회
	@GetMapping("/salesOutputSelect")
	public List<SalesOutMatDto> salesOutputSelect(SearchDto searchDto) {
		return salesOutputService.salesOutputSelect(searchDto);
	}
	
	// salesOutMat 조회
	@GetMapping("/SSM_Search")
	public List<Sales_OutMat_tbl> salesOutMatSelectDao(Sales_OutMat_tbl sales_OutMat_tbl) {
		return salesOutputService.salesOutMatSelectDao(sales_OutMat_tbl);
	}

	// sales_Output_insert
	@PostMapping("/SOM_Save")
	public int salesOutMatInsert(@RequestParam("masterData") String masterData, @RequestParam("subData") String subData,
			Principal principal) {
		System.out.println("masterData = " + masterData);
		System.out.println("subData = " + subData);
		ObjectMapper mapper = new ObjectMapper();

		try {

			SalesOutputOrderMasterDto salesOutputOrderMasterDto = mapper.readValue(masterData,
					SalesOutputOrderMasterDto.class);

			List<Sales_OutMat_tbl> sales_OutMat_List = Arrays
					.asList(mapper.readValue(subData, Sales_OutMat_tbl[].class));

			return salesOutputService.salesOutMatInsert(salesOutputOrderMasterDto, sales_OutMat_List,
					principal.getName());

		} catch (Exception e) {

			e.printStackTrace();
			return 0;

		}

	}
	
	// sales_Output_insert
	@PostMapping("/salesOutputSave")
	public int salesOutputSave(@RequestBody List<SalesOutMatDto> SalesOutMatDtoList, Principal principal) {
		return salesOutputService.salesOutputInsert(SalesOutMatDtoList, principal.getName());
	}

	// selecOutMat List select
	@GetMapping("/SOL_Search")
	public List<Sales_OutMat_tbl> salesOutMatSelectListDao(Sales_OutMat_tbl sales_OutMat_tbl, SearchDto searchDto) {
		return salesOutputService.salesOutMatSelectListDao(sales_OutMat_tbl, searchDto);
	}

	// salesOutMat Item View
	@GetMapping("/SOIV_Search")
	public List<Sales_OutMat_tbl> salesOutMatItemViewDao(Sales_OutMat_tbl sales_OutMat_tbl, SearchDto searchDto) {
		return salesOutputService.salesOutMatItemViewDao(sales_OutMat_tbl, searchDto);
	}

	// salesOutMat Item View
	@GetMapping("/SOCV_Search")
	public List<Sales_OutMat_tbl> salesOutMatCustomerViewDao(Sales_OutMat_tbl sales_OutMat_tbl, SearchDto searchDto) {
		return salesOutputService.salesOutMatCustomerViewDao(sales_OutMat_tbl, searchDto);
	}

	// 반품할 목록 조회
	@GetMapping("/SORI_Search")
	public List<Sales_OutMat_tbl> salesOutMatReturnSelectDao(SearchDto searchDto) {
		return salesOutputService.salesOutMatReturnSelectDao(searchDto);
	}

	// 반품 조회
	@GetMapping("/SORS_Search")
	public List<Sales_OutMat_tbl> salesOutMatReturnListDao(SearchDto searchDto) {
		return salesOutputService.salesOutMatReturnListDao(searchDto);
	}

}
