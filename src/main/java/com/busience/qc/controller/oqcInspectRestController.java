package com.busience.qc.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.OQC_Inspect_Dto;
import com.busience.qc.service.OQCInspectService;
import com.busience.sales.dto.Sales_OutMat_tbl;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	//저장
	@PostMapping("/SOI_Save")
	public int oqcInspectInsertDao(HttpServletRequest request) {
		
		String dataList = request.getParameter("dataList");
		String packList = request.getParameter("packList");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			List<OQC_Inspect_Dto> OQCInspectDtoList = Arrays.asList(mapper.readValue(dataList, OQC_Inspect_Dto[].class));
			
			List<OQC_Inspect_Dto> OQCPackUnitDtoList = Arrays.asList(mapper.readValue(packList, OQC_Inspect_Dto[].class));
		
			return oqcInspectService.oqcInspectInsertDao(OQCInspectDtoList, OQCPackUnitDtoList);
					
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
