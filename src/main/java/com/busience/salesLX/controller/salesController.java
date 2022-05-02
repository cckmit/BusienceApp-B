package com.busience.salesLX.controller;

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
	@GetMapping("salesOrderLX")
	public String salesOrder(Model model) {
		
		model.addAttribute("pageName", "수주 관리");
		
		return "sales/salesOrderLX";
	}

	// salesOrderLXPrint
	@GetMapping("salesOrderLXPrint")
	public String salesOrderLXPrint() {
		return "sales/salesPrintLX";
	}
	
	// salesOrderListLX
	@GetMapping("salesOrderListLX")
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
	
	//salesInputLX
	@GetMapping("salesInput")
	public String salesInput(Model model) {

		// 입고구분
		int salesInputLX = 17;
		model.addAttribute("salesInput", dtlService.getDtl(salesInputLX));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 관리");

		return "sales/salesInput";
	}
	
	//salesInputLX_another
	@GetMapping("salesInputLX_another")
	public String salesInputLX_another(Model model) {

		// 입고구분
		int salesInputLX = 17;
		model.addAttribute("salesInputLX", dtlService.getDtl(salesInputLX));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 관리 (수동)");

		return "salesLX/salesInputLX_another";
	}

	// salesOutputLX
	@GetMapping("salesOutput")
	public String salesOutput(Model model) {
		
		model.addAttribute("pageName", "제품 출고 관리");
		
		return "sales/salesOutput";
	}
	
	// salesInReturn
	@GetMapping("salesInReturn")
	public String salesInReturn(Model model) {
		
		model.addAttribute("pageName", "입고 반품 관리");
		
		return "sales/salesInReturn/salesInReturnMaster";
	}
	
	// salesOutReturnLX
	@GetMapping("salesOutReturnLX")
	public String salesOutReturn(Model model) {

		model.addAttribute("pageName", "판매 반품 관리");

		return "sales/salesOutReturnLX/salesOutReturnLXMaster";
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
	
	// salesInoutListLX
	@GetMapping("salesInoutListLX")
	public String salesInoutList(Model model) {
		
		model.addAttribute("pageName", "제품 입출고 현황");
		
		return "salesLX/salesInoutListLX";
	}

	// salesDeliveryMaster
	@GetMapping("salesDeliveryLXMaster")
	public String salesDeliveryMaster(Model model) {
		
		// 출고현황
		int OutputType = 19;
		model.addAttribute("OutputType", dtlService.getDtl(OutputType));
		
		// 메뉴명
		model.addAttribute("pageName", "납품 현황 조회");
		
		return "salesLX/salesDelivery/salesDeliveryMaster";
	}
	
	// salesStockLXMaster
	@GetMapping("salesStockLXMaster")
	public String salesStockMaster(Model model) {
		
		model.addAttribute("pageName", "제품 재고 조회");
		
		return "salesLX/salesStock/salesStockMaster";
	}
	
	// salesLabelPrint
	@GetMapping("salesLabelPrint")
	public String salesLabelPrint(Model model) {
		
		model.addAttribute("pageName", "라벨 출력");
		
		return "salesLX/salesLabelPrint";
	}
}
