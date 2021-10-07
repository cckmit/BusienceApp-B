package com.busience.salesLX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DTL_Service;

@Controller
public class salesLXController {

	private DTL_Service dtl_Service;

	public salesLXController(DTL_Service dtl_Service) {
		this.dtl_Service = dtl_Service;
	}
	
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
		String salesInputLX = Integer.toString(17);
		model.addAttribute("salesInputLX", dtl_Service.getAlldtl(salesInputLX));
		
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
		String salesInputMaster = Integer.toString(17);
		model.addAttribute("salesInputMaster", dtl_Service.getAlldtl(salesInputMaster));
		
		// 메뉴명
		model.addAttribute("pageName", "제품 입고 조회");

		return "salesLX/salesInput/salesInputMaster";
	}
	
	// salesOutputMaster
	@GetMapping("salesOutputLXMaster")
	public String salesOutputMaster(Model model) {
		
		// 출하구분
		String salesOutputMaster = Integer.toString(19);
		model.addAttribute("salesOutputMaster", dtl_Service.getAlldtl(salesOutputMaster));
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
		String OutputType = Integer.toString(19);
		model.addAttribute("OutputType", dtl_Service.getAlldtl(OutputType));
		
		// 현재연월
		String PrcsDate = Integer.toString(20);
		String PrcsNum = Integer.toString(6);
		model.addAttribute("PrcsDate", dtl_Service.getDate(PrcsDate, PrcsNum));
		
		// 전월
		String LastMonth = Integer.toString(20);
		String LastNum = Integer.toString(4);
		model.addAttribute("LastMonth", dtl_Service.getDate(LastMonth, LastNum));
		
		// 마지막날
		String LastDay = Integer.toString(2);
		model.addAttribute("LastDay", dtl_Service.getLastDay(LastDay));
		
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
