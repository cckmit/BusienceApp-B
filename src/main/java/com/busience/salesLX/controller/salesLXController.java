package com.busience.salesLX.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class salesLXController {

	@Autowired
	DtlService dtlService;

	// salesOrderMaster
	@GetMapping("salesOrderLX")
	public String salesOrder(Model model) {
		
		model.addAttribute("pageName", "수주 관리");
		
		return "salesLX/salesOrderLX";
	}

	// salesOrderLXPrint
	@GetMapping("salesOrderLXPrint")
	public String salesOrderLXPrint() {
		return "salesLX/salesPrintLX";
	}
	
	// salesOrderListLX
	@GetMapping("salesOrderListLX")
	public String salesOrderList(Model model) {
		
		model.addAttribute("pageName", "수주 조회");
		
		return "salesLX/salesOrderList";
	}
	
	//salesInputLX
	@GetMapping("salesInputLX")
	public String salesInputLX(Model model) {

		// 입고구분
		int salesInputLX = 17;
		model.addAttribute("salesInputLX", dtlService.getAlldtl(salesInputLX));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 관리");

		return "salesLX/salesInputLX";
	}

	// salesOutputLX
	@GetMapping("salesOutputLX")
	public String salesOutputLX(Model model) {
		
		model.addAttribute("pageName", "제품 출고 관리");
		
		return "salesLX/salesOutputLX";
	}
	
	// salesInReturn
	@GetMapping("salesInReturnLX")
	public String salesInReturn(Model model) {
		
		model.addAttribute("pageName", "입고 반품 관리");
		
		return "salesLX/salesInReturnLX/salesInReturnLXMaster";
	}
	
	// salesOutReturnLX
	@GetMapping("salesOutReturnLX")
	public String salesOutReturn(Model model) {

		model.addAttribute("pageName", "판매 반품 관리");

		return "salesLX/salesOutReturnLX/salesOutReturnLXMaster";
	}
	
	// salesInputMaster
	@GetMapping("salesInputLXMaster")
	public String salesInputMaster(Model model) {

		// 입고구분
		int InputType = 17;
		model.addAttribute("InputType", dtlService.getAlldtl(InputType));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 조회");

		return "salesLX/salesInput/salesInputMaster";
	}
	
	// salesOutputMaster
	@GetMapping("salesOutputLXMaster")
	public String salesOutputMaster(Model model) {
		
		// 출하구분
		int OutputType = 19;
		model.addAttribute("OutputType", dtlService.getAlldtl(OutputType));
		// 메뉴명
		model.addAttribute("pageName", "제품 출하 조회");
		
		return "salesLX/salesOutput/salesOutputMaster";
	}
	
	// salesInoutListLX
	@GetMapping("salesInoutListLX")
	public String salesInoutList(Model model) {
		
		model.addAttribute("pageName", "제품 입출고 조회");
		
		return "salesLX/salesInoutListLX";
	}

	// salesDeliveryMaster
	@GetMapping("salesDeliveryLXMaster")
	public String salesDeliveryMaster(Model model) {
		
		// 출고현황
		int OutputType = 19;
		model.addAttribute("OutputType", dtlService.getAlldtl(OutputType));
		
		// 현재연월
		int PrcsDate = 20;
		String PrcsNum = Integer.toString(6);
		model.addAttribute("PrcsDate", dtlService.getDate(PrcsDate, PrcsNum));
		
		// 전월
		int LastMonth = 20;
		String LastNum = Integer.toString(4);
		model.addAttribute("LastMonth", dtlService.getDate(LastMonth, LastNum));
		
		// 마지막날
		int LastDay = 2;
		model.addAttribute("LastDay", dtlService.getLastDay(LastDay));
		
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
}
