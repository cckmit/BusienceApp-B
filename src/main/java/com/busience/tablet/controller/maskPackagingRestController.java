package com.busience.tablet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.dto.LabelPrintDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.production.service.EquipWorkOrderService;
import com.busience.production.service.SmallPackagingService;
import com.busience.sales.service.SalesPackingService;
import com.busience.tablet.service.MaskPackagingService;

@RestController("maskPackagingRestController")
@RequestMapping("/tablet/maskPackagingRest")
public class maskPackagingRestController {

	@Autowired
	MaskPackagingService maskPackagingService;
	
	@Autowired
	EquipWorkOrderService equipWorkOrderService;
	
	@Autowired
	SmallPackagingService smallPackagingService;
	
	@Autowired
	SalesPackingService salesPackingService;
	
	@GetMapping("/packagingLineListSelect")
	public List<EquipWorkOrderDto> packagingLineListSelect(SearchDto searchDto) {
		return equipWorkOrderService.packagingLineListSelect(searchDto);
	}
	
	@GetMapping("/smallPackagingStandbySelect")
	public List<Small_Packaging_tbl> smallPackagingStandbySelect(SearchDto searchDto) {
		return smallPackagingService.smallPackagingStandbySelect(searchDto);
	}
	
	@PostMapping("/smallPackagingSave")
	public LabelPrintDto smallPackagingSave(SearchDto searchDto) {
		return maskPackagingService.smallPackagingSave(searchDto);
	}
	
	@PostMapping("/largePackagingSave")
	public LabelPrintDto largePackagingSave(SearchDto searchDto) {
		return salesPackingService.largePackagingInsert(searchDto);
	}
	
	@GetMapping("/smallPackagingQtySelect")
	public int smallPackagingQtySelect(SearchDto searchDto) {
		return smallPackagingService.smallPackagingQtySelect(searchDto);
	}
	
	@GetMapping("/largePackagingQtySelect")
	public int largePackagingQtySelect(SearchDto searchDto) {
		return salesPackingService.largePackagingQtySelect(searchDto);
	}
}
