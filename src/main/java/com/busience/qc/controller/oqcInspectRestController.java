package com.busience.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.qc.service.OQCInspectService;
import com.busience.sales.dto.Sales_OutMat_tbl;

@RestController("oqcInspectRestController")
@RequestMapping("oqcInspectRest")
public class oqcInspectRestController {

	@Autowired
	OQCInspectService oqcInspectService;
	
	//영업 출고검사 LotMaster 조회
	@GetMapping("SOIL_Search")
	public List<Sales_OutMat_tbl> salesOutMatInspectList(SearchDto searchDto) {
		return oqcInspectService.salesOutMatInspectList(searchDto);
	}
}
