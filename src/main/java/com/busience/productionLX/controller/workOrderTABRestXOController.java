package com.busience.productionLX.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.productionLX.service.WorkOrderService;

@RestController("workOrderTABRestXOController")
@RequestMapping("workOrderTABRestXO")
public class workOrderTABRestXOController {

	@Autowired
	WorkOrderService workOrderService;
		
	@GetMapping("/WOT_Search")
	public List<WorkOrderDto> WOT_Search(SearchDto searchDto){
		return workOrderService.workOrderCompleteSelect(searchDto);
	}
	
	@GetMapping("/WOT_Choice")
	public List<WorkOrderDto> WOT_Choice(SearchDto searchDto){
		return workOrderService.workOrderChoiceSelectDao(searchDto);
	}
	
	@GetMapping("/workOrderSumQty")
	public int workOrderSumQty(SearchDto searchDto) {
		return workOrderService.workOrderSumQty(searchDto);
	}
	
	@GetMapping("/workOrderUpdate")
	public int workOrderUpdate(WorkOrderDto workOrderDto) {
		return workOrderService.workOrderUpdate(workOrderDto);
	}
	
	//태블릿화면에서 작업지시저장
	@GetMapping("/WOT_Save")
	public int WOT_Save(WorkOrderDto workOrderDto) {
		List<WorkOrderDto> WorkOrderDtoList = new ArrayList<WorkOrderDto>();
				
		WorkOrderDtoList.add(0, workOrderDto);		
		
		return workOrderService.workOrderRegister(WorkOrderDtoList, "admin");
	}
	
	//마지막 생산수량 체크
	@GetMapping("/lastProductQty")
	public int lastProductQty(ProductionMgmtDto productionMgmtDto) {
		return workOrderService.lastProductQty(productionMgmtDto);
	}
	
	//마지막 생산수량 체크
	@GetMapping("/lastProductModify")
	public int lastProductModify(ProductionMgmtDto productionMgmtDto) {
		return workOrderService.lastProductModify(productionMgmtDto);
	}
}