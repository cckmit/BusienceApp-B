package com.busience.production.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.common.service.DtlService;
import com.busience.production.dto.WorkOrderDto;
import com.busience.production.service.WorkOrderService;
import com.busience.sales.dto.SalesOrderMasterDto;

@RestController("workOrderRestController")
@RequestMapping("workOrderRest")
public class workOrderRestController {

	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	DtlService dtlService;
	
	//작업지시 접수 리스트
	@GetMapping("/workOrderSelect")
	public List<WorkOrderDto> workOrderSelect(SearchDto searchDto) {
		String[] statusCodeArr = {dtlService.getAllDtl(29).get(1).getCHILD_TBL_NO()};;
		searchDto.setStatusCodeArr(statusCodeArr);
		return workOrderService.workOrderSelect(searchDto);
	}
	
	// 수주현황
	@GetMapping("/workOrderSalesOrderSelect")
	public List<SalesOrderMasterDto> workOrderSalesOrderSelect(SearchDto searchDto) {
		return workOrderService.workOrderSalesOrderSelect(searchDto);
	}
	
	//작업지시 등록
	@PostMapping("/workOrderRegister")
	public int workOrderRegister(@RequestBody List<WorkOrderDto> workOrderDtoList, Principal principal) {
		return workOrderService.workOrderRegister(workOrderDtoList, principal.getName());
	}
	
	//작업지시 삭제
	@DeleteMapping("/workOrderDelete")
	public int workOrderDelete(@RequestBody List<WorkOrderDto> workOrderDtoList) {
		return workOrderService.workOrderDelete(workOrderDtoList);
	}
	
	
	//----------------------------
	
	//작업지시 등록된 리스트
	@GetMapping("/workOrderSubSelect")
	public List<WorkOrderDto> workOrderSubSelect(SearchDto searchDto) {
		return workOrderService.workOrderSubSelect(searchDto);
	}
	
	//작업지시 테이블 설비 검색
	@GetMapping("/workOrderChoice")
	public List<WorkOrderDto> workOrderChoiceSelect(SearchDto searchDto) {
		return workOrderService.workOrderChoiceSelectDao(searchDto);
	}
	
	//작업종료 update
	@PostMapping("/workOrderUpdate")
	public int workOrderUpdate(WorkOrderDto workOrderDto, Principal principal) {
		return workOrderService.workOrderFinalUpdate(workOrderDto, principal.getName());
	}
}
