package com.busience.production.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.service.ProductionMgmtService;
import com.busience.tablet.dto.CrateLotDto;

@RestController("proSumRestController")
@RequestMapping("proSumRest")
public class proSumRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ProductionMgmtService productionMgmtService;

	@Autowired
	JdbcTemplate jdbctemplate;
	
	// 생산 실적 관리(설비별)
	@GetMapping("/proMachineSumSelect2")
	public List<CrateLotDto> proMachineSumSelect(SearchDto searchDto) {
		return productionMgmtService.proMachineSumList(searchDto);
	}

	// 생산 실적 관리(제품별)
	@GetMapping("/proItemSumSelect2")
	public List<CrateLotDto> proItemSumSelect(SearchDto searchDto) {
		return productionMgmtService.proItemSumList(searchDto);
	}
	
	// 마스크 실적 현황
	@GetMapping("/proMaskSumSelect")
	public List<CrateLotDto> proMaskSumSelect(SearchDto searchDto) {
		return productionMgmtService.proMaskSumDao(searchDto);
	}
	
	// 생산 포장 현황
	@GetMapping("/proPackingSumSelect")
	public List<CrateLotDto> proPackingSumSelect(SearchDto searchDto) {
		return productionMgmtService.proPackingDao(searchDto);
	}
}
