package com.busience.sales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.service.SalesPackingService;

@RestController("salesPackingRestController")
@RequestMapping("salesPackingRest")
public class salesPackingRestController {
	
	@Autowired
	SalesPackingService salesPackingService;
	
	// sales_packing_select
	@GetMapping("/SP_Search")
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto) {
		return salesPackingService.salesPackingListSelectDao(searchDto);
	}
	
	/*
	 * // 입고 반품 조회
	 * 
	 * @GetMapping("/SIRI_Search") public List<SalesPackingDto>
	 * salesInMatReturnSelectDao(SearchDto searchDto) { return
	 * salesPackingService.salesInMatReturnSelectDao(searchDto); }
	 */
	
	// 입고 반품 리스트
	@GetMapping("/SIL_Search")
	public List<SalesPackingDto> salesInMatReturnListDao(SearchDto searchDto) {
		return salesPackingService.salesInMatReturnListDao(searchDto);
	}
	
	// 포장 관리 조회
	@GetMapping("/SPS_Search")
	public List<SalesPackingDto> salesPackingSelectDao(SearchDto searchDto) {
		return salesPackingService.salesPackingSelectDao(searchDto);
	}
	
	// 대포장 Lot 조회
	@GetMapping("/LargeLot_Search")
	public List<SalesPackingDto> salesLargePackingLotNoDao(SearchDto searchDto) {
		return salesPackingService.salesLargePackingLotNoDao(searchDto);
	}
	
	// 소포장 Lot 조회
	@GetMapping("/SmallLot_Search")
	public List<Small_Packaging_tbl> salesSmallPackingLotNoDao(SearchDto searchDto) {
		return salesPackingService.salesSmallPackingLotNoDao(searchDto);
	}
	
	@GetMapping("/SmallLot_Search2")
	public List<Small_Packaging_tbl> salesSmallPackingLotNo2Dao(SearchDto searchDto) {
		return salesPackingService.salesSmallPackingLotNo2Dao(searchDto);
	}
}
