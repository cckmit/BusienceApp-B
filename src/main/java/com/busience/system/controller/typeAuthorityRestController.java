package com.busience.system.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.DtlDto;
import com.busience.common.service.DtlService;
import com.busience.system.dto.RightsMgmtDto;
import com.busience.system.service.RightsMgmtService;

@RestController("typeAuthorityRestController")
@RequestMapping("typeAuthorityRest")
public class typeAuthorityRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	RightsMgmtService rightsMgmtService;

	//TAM_Search
	@GetMapping("/TAM_Search")
	public List<DtlDto> TAM_Search() {
		return dtlService.getAlldtl(1);
	}
	
	//TAS_Search
	@GetMapping("/TAS_Search")
	public List<RightsMgmtDto> TAS_Search(String userType) {
		return rightsMgmtService.rightsMgmtList(userType);
	}
	
	// TA_Update
	@PostMapping("/TA_Update")
	public int TA_Update(@RequestBody List<RightsMgmtDto> jsonDataList) {
		return rightsMgmtService.rightsMgmtUpdate(jsonDataList);
	}
}
