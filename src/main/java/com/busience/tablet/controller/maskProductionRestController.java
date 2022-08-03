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

@RestController("maskProductionRestController")
@RequestMapping("/tablet/maskProductionRest")
public class maskProductionRestController {

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

	@GetMapping("/rawMaterialMasterSelect")
	public List<RawMaterialMasterDto> rawMaterialMasterSelect(SearchDto searchDto) {
		return maskProductionService.rawMaterialMasterSelect(searchDto);
	}

	@GetMapping("/rawMaterialRecordSelect")
	public List<RawMaterialMasterDto> rawMaterialRecordSelect(SearchDto searchDto) {
		return maskProductionService.rawMaterialRecordSelect(searchDto);
	}

	@GetMapping("/rawMaterialSelect")
	public List<RawMaterialDto> rawMaterialSelect(SearchDto searchDto) {
		return maskProductionService.rawMaterialSelect(searchDto);
	}

	@GetMapping("/workingByMachine")
	public ItemDto workingByMachine(SearchDto searchDto) {
		return maskProductionService.workingSelectByMachine(searchDto);
	}

	@GetMapping("/BOMBOMList")
	public List<BOMDto> BOMBOMList(SearchDto searchDto) {
		return bomService.BOMBOMList(searchDto);
	}
	
    @GetMapping("/rawMaterialBOMList")
    public List<BOMDto> RawMaterialBOMList(SearchDto searchDto) {
        return bomService.RawMaterialBOMList(searchDto);
    }

	@PostMapping("/rawMaterialSave")
	public String rawMaterialSave(@RequestParam("masterData") String masterData,
			@RequestParam("subData") String subData) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			RawMaterialDto rawMaterialDto = mapper.readValue(masterData, RawMaterialDto.class);

			List<RawMaterialSubDto> rawMaterialSubDtoList = Arrays
					.asList(mapper.readValue(subData, RawMaterialSubDto[].class));

			return maskProductionService.rawMaterialSave(rawMaterialDto, rawMaterialSubDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	@GetMapping("/crateLotRecordSelect")
	public List<CrateLotDto> crateLotRecordSelect(SearchDto searchDto) {
		return maskProductionService.crateLotRecordSelect(searchDto);
	}
/*
	@PostMapping("/crateSave")
	public CrateDto crateSave(CrateDto crateDto) {
		return maskProductionService.crateSave(crateDto);
	}*/

	@PostMapping("/crateProductionSave")
	public int crateProductionSave(CrateProductionDto crateProductionDto) {
		return maskProductionService.crateProductionSave(crateProductionDto);
	}

	@PostMapping("/workOrderStart")
	public int workOrderStart(WorkOrderDto workOrderDto) {
		return workOrderService.workOrderStatusUpdate(workOrderDto);
	}

	@GetMapping("/CrateStatusCheck")
	public CrateDto CrateStatusCheck(SearchDto searchDto) {
		return maskProductionService.CrateStatusCheck(searchDto);
	}

	@GetMapping("/crateLotSelect")
	public List<CrateLotDto> crateLotSelectList(SearchDto searchDto) {
		return maskProductionService.crateLotSelectList(searchDto);
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
	/*
	@PostMapping("/lotInput")
	public String lotInput(RawMaterialDto rawMaterialDto) {
		return maskProductionService.lotInput(rawMaterialDto);
	}*/
	
	@PostMapping("/workComplete")
	public String workComplete(RawMaterialDto rawMaterialDto) {
		return maskProductionService.workComplete(rawMaterialDto);
	}
}
