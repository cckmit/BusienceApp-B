package com.busience.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.DtlDto;
import com.busience.common.service.DtlService;
import com.busience.system.dto.CmnDto;
import com.busience.system.service.CmnService;

@RestController("codeManageRestController")
@RequestMapping("codeManageRest")
public class codeManageRestController {

	@Autowired
	CmnService cmnService;
	
	@Autowired
	DtlService dtlService;
	
	//CMM_Search
	@GetMapping("/CMM_Search")
	public List<CmnDto> CMM_Search() {
		return cmnService.cmnList();
	}

	@GetMapping("/CMS_Search")
	public List<DtlDto> CMS_Search(int NEW_TBL_CODE) {
		return dtlService.getAlldtl(NEW_TBL_CODE);
	}

	@RequestMapping("/insert")
	public int insert(DtlDto dtlDto) {
		return dtlService.dtlInsert(dtlDto);
	}

	@RequestMapping("/update")
	public int update(DtlDto dtlDto) {
		return dtlService.dtlUpdate(dtlDto);
	}
}
