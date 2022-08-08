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
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.CrateService;
import com.busience.production.service.WorkOrderService;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.service.BOMService;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.CrateProductionDto;
import com.busience.tablet.dto.RawMaterialDto;
import com.busience.tablet.dto.RawMaterialMasterDto;
import com.busience.tablet.dto.RawMaterialSubDto;
import com.busience.tablet.service.MaskProductionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("maskProductionFacilityRestController")
@RequestMapping("/tablet/maskProductionFacilityRest")
public class maskProductionFacilityRestController {

	@Autowired
	MaskProductionService maskProductionService;

	@Autowired
	WorkOrderService workOrderService;

	@Autowired
	BOMService bomService;
	
	@Autowired
	CrateService crateService;
	
	@Autowired
	ProductionMonitoringService productionMonitoringService;


	@GetMapping("/BOMBOMList")
	public List<BOMDto> BOMBOMList(SearchDto searchDto) {
		return bomService.BOMBOMList(searchDto);
	}
	
    @GetMapping("/RawMaterialBOMList")
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
	public CrateDto crateSelectByMachine(SearchDto searchDto) {
		return crateService.crateSelectByMachine(searchDto);
	}


	@PostMapping("/workOrderStart")
	public int workOrderStart(WorkOrderDto workOrderDto) {
		return workOrderService.workOrderStatusUpdate(workOrderDto);
	}

	@GetMapping("/CrateStatusCheck")
	public CrateDto CrateStatusCheck(SearchDto searchDto) {
		return maskProductionService.CrateStatusCheck(searchDto);
	}
}
