package com.busience.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class salesController {

	@Autowired
	DtlService dtlService;

	// salesOrderMaster
	@GetMapping("salesOrder")
	public String salesOrder(Model model) {
		
		model.addAttribute("pageName", "수주 관리");
		
		return "sales/salesOrder";
	}

	// salesOrderPrint
	@GetMapping("salesOrderPrint")
	public String salesOrderPrint() {
		return "sales/salesPrint";
	}
	
	// salesOrderList
	@GetMapping("salesOrderList")
	public String salesOrderList(Model model) {
		
		model.addAttribute("pageName", "수주 조회");
		
		return "sales/salesOrderList";
	}
	
	// salesOutputOrder
	@GetMapping("salesOutputOrder")
	public String salesOutputOrder(Model model) {
		
		model.addAttribute("pageName", "출하 지시 관리");
		
		return "sales/salesOutputOrder";
	}
	
	// salesOutputOrderList
	@GetMapping("salesOutputOrderList")
	public String salesOutputOrderList(Model model) {
		
		model.addAttribute("pageName", "출하 지시 조회");
		
		return "sales/salesOutputOrderList";
	}
	
	//salesInput
	@GetMapping("salesInput")
	public String salesInput(Model model) {

		// 입고구분
		int salesInput = 17;
		model.addAttribute("salesInput", dtlService.getDtl(salesInput));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 관리");
		if(dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS()) {
			return "sales/salesInput";
		}else {
			return "sales/salesInputLX";
		}
	}
	
	//salesInput_another
	@GetMapping("salesInput_another")
	public String salesInput_another(Model model) {

		// 입고구분
		int salesInput = 17;
		model.addAttribute("salesInput", dtlService.getDtl(salesInput));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 관리 (수동)");

		return "sales/salesInput_another";
	}

	// salesOutput
	@GetMapping("salesOutput")
	public String salesOutput(Model model) {
		
		model.addAttribute("pageName", "제품 출고 관리");
		
		if(dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS()) {
			return "sales/salesOutput";
		}else {
			return "sales/salesOutputLX";
		}
	}
	
	// salesInReturn
	@GetMapping("salesInReturn")
	public String salesInReturn(Model model) {
		
		model.addAttribute("pageName", "입고 반품 관리");
		
		if(dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS()) {
			return "sales/salesInReturn/salesInReturnMaster";
		}else {
			return "sales/salesInReturn/salesInReturnLXMaster";
		}
	}
	
	// salesOutReturn
	@GetMapping("salesOutReturn")
	public String salesOutReturn(Model model) {

		model.addAttribute("pageName", "판매 반품 관리");

		return "sales/salesOutReturn/salesOutReturnMaster";
	}
	
	// salesInputMaster
	@GetMapping("salesInputMaster")
	public String salesInputMaster(Model model) {

		// 입고구분
		int InputType = 17;
		model.addAttribute("InputType", dtlService.getDtl(InputType));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 조회");

		return "sales/salesInput/salesInputMaster";
	}
	
	// salesOutputMaster
	@GetMapping("salesOutputMaster")
	public String salesOutputMaster(Model model) {
		
		// 출하구분
		int OutputType = 19;
		model.addAttribute("OutputType", dtlService.getDtl(OutputType));
		// 메뉴명
		model.addAttribute("pageName", "제품 출하 조회");
		
		return "sales/salesOutput/salesOutputMaster";
	}
	
	// salesInoutList
	@GetMapping("salesInoutList")
	public String salesInoutList(Model model) {
		
		model.addAttribute("pageName", "제품 입출고 현황");
		
		return "sales/salesInoutList";
	}

	// salesDeliveryMaster
	@GetMapping("salesDeliveryMaster")
	public String salesDeliveryMaster(Model model) {
		
		// 출고현황
		int OutputType = 19;
		model.addAttribute("OutputType", dtlService.getDtl(OutputType));
		
		// 메뉴명
		model.addAttribute("pageName", "납품 현황 조회");
		
		return "sales/salesDelivery/salesDeliveryMaster";
	}
	
	// salesStockMaster
	@GetMapping("salesStockMaster")
	public String salesStockMaster(Model model) {
		
		model.addAttribute("pageName", "제품 재고 조회");
		model.addAttribute("lotUse", dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS());
		return "sales/salesStock/salesStockMaster";
	}
	
	// salesLabelPrint
	@GetMapping("salesLabelPrint")
	public String salesLabelPrint(Model model) {
		
		model.addAttribute("pageName", "라벨 출력");
		
		return "sales/salesLabelPrint";
	}
	
	// salesPackingList
	@GetMapping("salesPackingList")
	public String salesPackingList(Model model) {
		
		// 입고구분
		int InputType = 17;
		model.addAttribute("InputType", dtlService.getDtl(InputType));
		
		model.addAttribute("pageName", "포장 관리 조회");
		
		return "sales/salesPackingList";
	}
}
