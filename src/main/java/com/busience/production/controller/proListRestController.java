package com.busience.production.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.service.ProductionMgmtService;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.RawMaterialDto;

@RestController("proListRestController")
@RequestMapping("proListRest")
public class proListRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbctemplate;

	@Autowired
	ProductionMgmtService productionMgmtService;

	@GetMapping("/proItemTestSelect")
	public List<ProductionMgmtDto> proItemTestSelect(SearchDto searchDto) {
		// 코드가 비어있으면 이름으로 대체
		if (searchDto.getItemCode().length() == 0) {
			searchDto.setItemCode(searchDto.getItemName());
		}
		return productionMgmtService.proItemList(searchDto);
	}

	@GetMapping("/proMachineTestSelect")
	public List<CrateLotDto> proMachineTestSelect(SearchDto searchDto) { // 코드가 비어있으면 이름으로 대체
		if (searchDto.getMachineCode().length() == 0) {
			searchDto.setMachineCode(searchDto.getMachineName());
		}
		return productionMgmtService.proMachineList(searchDto);
	}

	// Lot 발행 조회
	@GetMapping("/LotIssueList")
	public List<ProductionMgmtDto> lotIssueListDao(SearchDto searchDto) {
		return productionMgmtService.lotIssueListDao(searchDto);
	}

	// Lot 이력 조회
	@GetMapping("/crateLotList")
	public List<CrateLotDto> crateLotListMasterDao(SearchDto searchDto) {
		return productionMgmtService.crateLotListMasterDao(searchDto);
	}

	// Lot 이력 조회
	@GetMapping("/rawLotList")
	public List<RawMaterialDto> rawMaterialListMasterDao(SearchDto searchDto) {
		return productionMgmtService.rawMaterialListMasterDao(searchDto);
	}
}
