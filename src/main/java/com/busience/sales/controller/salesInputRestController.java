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

import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.common.service.DtlService;
import com.busience.material.dto.LotMasterDto;
import com.busience.sales.dto.SalesInMatDto;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.dto.Sales_InMat_tbl;
import com.busience.sales.service.SalesInputService;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesInputRestController")
@RequestMapping("salesInputRest")
public class salesInputRestController {

	@Autowired
	SalesInputService salesInputService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	DtlService dtlservice;
	
	@GetMapping("/salesInputSelect")
	public List<ItemDto> salesInputSelect(){
		List<DtlDto> dtlDtoList = dtlservice.getDtl(5);
		
		String materialClsfc = "";
		for(int i=0;i<dtlDtoList.size();i++) {
			if("완제품".equals(dtlDtoList.get(i).getCHILD_TBL_TYPE()))
			materialClsfc = dtlDtoList.get(i).getCHILD_TBL_NO();
		}
		return itemService.selectMaterialClsfc(materialClsfc);
	}
	
	@PostMapping("/salesInputSave")
	public int salesInputSave(@RequestBody List<SalesInMatDto> salesInMatDtoList, Principal principal) {
		return salesInputService.salesInputLXSave(salesInMatDtoList, principal.getName());
	}
	
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
