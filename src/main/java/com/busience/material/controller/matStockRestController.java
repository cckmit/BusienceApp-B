package com.busience.material.controller;

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
import com.busience.common.service.DtlService;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.StockDto;
import com.busience.material.service.StockService;
import com.busience.production.dto.LabelPrintDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("matStockRestController")
@RequestMapping("matStockRest")
public class matStockRestController {

	@Autowired
	StockService stockService;
	
	@Autowired
	DtlService dtlService;
	
	// 자재 재고현황
	@GetMapping("matStockSelect")
	public List<StockDto> matStockSelect(SearchDto searchDto) {
		//자재창고 설정
		searchDto.setWarehouse(dtlService.getDtl(10).get(0).getCHILD_TBL_NO());
		return stockService.stockSelect(searchDto);
	}
	
	// 현재고현황
	@GetMapping("stockSelect")
	public List<StockDto> stockSelect(SearchDto searchDto) {
		return stockService.stockSelect(searchDto);
	}
	
	// 재고 Lot- 품목 조회
	@GetMapping("matStockLotSelect")
	public List<StockDto> stockLotSelectDao(SearchDto searchDto) {
		return stockService.stockLotSelectDao(searchDto);
	}
	
	// 재고 조정
	@GetMapping("stockChangeSelect")
	public List<LotMasterDto> matStockChanageSelect(SearchDto searchDto) {
		return stockService.StockChanageSelect(searchDto);
	}
	
	// 재고 조정 저장
	@PostMapping("matStockChangeSave")
	public List<LabelPrintDto> matStockChangeSave(
			@RequestParam("selectedData") String selectedData,
			@RequestParam("warehouse") String string, Principal principal) {
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			List<LotMasterDto> LotMasterDtoList = Arrays.asList(mapper.readValue(selectedData, LotMasterDto[].class));

			String warehouse = mapper.readValue(string, String.class);
			
			return stockService.StockChangeSave(LotMasterDtoList, warehouse, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
