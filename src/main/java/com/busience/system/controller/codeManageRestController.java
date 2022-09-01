package com.busience.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return dtlService.getAllDtl(NEW_TBL_CODE);
	}

	@PostMapping("/codeManageInsert")
	public int codeManageInsert(DtlDto dtlDto) {
		return dtlService.dtlInsert(dtlDto);
	}

	@PutMapping("/codeManageUpdate")
	public int codeManageUpdate(DtlDto dtlDto) {
		System.out.println(dtlDto);
		return dtlService.dtlUpdate(dtlDto);
	}
}
