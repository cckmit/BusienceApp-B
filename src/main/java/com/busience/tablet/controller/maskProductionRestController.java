package com.busience.tablet.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.ChangedProductionStatusMonitoringDto;
import com.busience.monitoring.dto.ChangedWrappingStatusMonitoringDto;
import com.busience.monitoring.dto.ProductionStatusMonitoringDto;
import com.busience.monitoring.dto.WrappingStatusMonitoringDto;
import com.busience.monitoring.service.ProductionMonitoringService;
import com.busience.production.service.CrateService;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.service.BOMService;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.RawMaterialDto;
import com.busience.tablet.service.MaskProductionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("maskProductionRestController")
@RequestMapping("/tablet/maskProductionRest")
public class maskProductionRestController {

	@Autowired
	MaskProductionService maskProductionService;

	@Autowired
	BOMService bomService;
	
	@Autowired
	CrateService crateService;
	
	@Autowired
	ProductionMonitoringService productionMonitoringService;

    @GetMapping("/rawMaterialBOMList")
    public List<BOMDto> RawMaterialBOMList(SearchDto searchDto) {
        return bomService.RawMaterialBOMList(searchDto);
    }

	@GetMapping("/getProductionInfoFromMachine")
	public List<ProductionStatusMonitoringDto> getProductionInfoFromMachine(SearchDto searchDto) {
		return productionMonitoringService.selectProductionMonitoring(searchDto);
	}
	
	@GetMapping("/getWrappingInfoFromMachine")
	public List<WrappingStatusMonitoringDto> getWrappingInfoFromMachine(SearchDto searchDto) {
		return productionMonitoringService.selectWrappingMonitoring(searchDto);
	}
	
	@GetMapping("/getChangedProductionInfoFromMachine")
	public List<ChangedProductionStatusMonitoringDto> getChangedProductionInfoFromMachine(SearchDto searchDto) {
		return productionMonitoringService.selectChangedProductionMonitoring(searchDto);
	}
	
	@GetMapping("/getChangedWrappingInfoFromMachine")
	public List<ChangedWrappingStatusMonitoringDto> getChangedWrappingInfoFromMachine(SearchDto searchDto) {
		return productionMonitoringService.selectChangedWrappingMonitoring(searchDto);
	}	

	@GetMapping("/crateSelectByMachine")
	public List<CrateDto> crateSelectByMachine(SearchDto searchDto) {
		return crateService.crateSelectByMachine(searchDto);
	}

	@GetMapping("/CrateStatusCheck")
	public CrateDto CrateStatusCheck(SearchDto searchDto) {
		return maskProductionService.CrateStatusCheck(searchDto);
	}

	@PostMapping("/rawMaterialChange")
	public String rawMaterialChange(RawMaterialDto rawMaterialDto) {
		return maskProductionService.rawMaterialChange(rawMaterialDto);
	}
	
	@PostMapping("/crateChange")
	public CrateDto crateChange(
			@RequestParam("changeInfo") String changeInfo,
			@RequestParam("rawLotList") String rawLotList) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			CrateDto info = mapper.readValue(changeInfo, CrateDto.class);
			
			List<RawMaterialDto> RawMaterialDtoList = Arrays
					.asList(mapper.readValue(rawLotList, RawMaterialDto[].class));
			return maskProductionService.crateChange(info, RawMaterialDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/workComplete")
	public String workComplete(CrateDto crateDto) {
		return maskProductionService.workComplete(crateDto);
	}
}
