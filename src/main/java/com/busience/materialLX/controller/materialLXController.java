package com.busience.materialLX.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DTL_Service;

@Controller
public class materialLXController {
	
	private DTL_Service dtl_Service;
	
	public materialLXController(DTL_Service dtl_Service) {
		this.dtl_Service = dtl_Service;
	}
	
	//OrderMaster
	@GetMapping("matOrderLX")
	public String matOrderLX(Model model) {
		model.addAttribute("pageName", "발주 관리");
		return "materialLX/matOrderLX";
	}
	
	//OrderMaster
	@GetMapping("matOrderListLX")
	public String matOrderListLX(Model model) {
		model.addAttribute("pageName", "발주 조회");
		return "materialLX/matOrderListLX";
	}
	
	// MatInputLX
	@GetMapping("matInputLX")
	public String matInputLX(Model model) {
		model.addAttribute("pageName", "입고 관리");
		return "materialLX/matInputLX";
	}
	
	// MatOutputLX
	@GetMapping("matOutputLX")
	public String matOutputLX(Model model) {
		model.addAttribute("pageName", "출고 관리");
		return "materialLX/matOutputLX";
	}
	
	// matInReturnLX
	@GetMapping("matInReturnLX")
	public String orderMaster(Model model) {
		model.addAttribute("pageName", "입고 반품 관리");
		return "materialLX/matInReturnLX/matInReturnLXMaster";
	}
	
	//matInReturn
	@GetMapping("matOutReturnLX")
	public String matOutReturnLX(Model model) {
		model.addAttribute("pageName", "출고 반품 관리");
		return "materialLX/matOutReturnLX/matOutReturnLXMaster";
	}
	
	// matInputMasterLX
	@GetMapping("matInputMasterLX")
	public String matInputMasterLX(Model model, HttpServletRequest request) {
			
		// 입고구분
		String InMatType = Integer.toString(17);
		model.addAttribute("InMatType", dtl_Service.getAlldtl(InMatType));
		// 마지막날
		String LastDay = Integer.toString(2);
		model.addAttribute("LastDay", dtl_Service.getLastDay(LastDay));
		// 현재연월
		String PrcsDate = Integer.toString(20);
		String PrcsNum = Integer.toString(3);
		model.addAttribute("PrcsDate", dtl_Service.getDate(PrcsDate, PrcsNum));
		// 전월
		String LastMonth = Integer.toString(20);
		String LastNum = Integer.toString(1);
		model.addAttribute("LastMonth", dtl_Service.getDate(LastMonth, LastNum));
		// 메뉴명
		model.addAttribute("pageName", "입고 현황");
				
		return "materialLX/matInputLX/matInputMasterLX";
	}
	
	// matOutputMasterLX
	@GetMapping("matOutputMasterLX")
	public String matOutputMasterLX(Model model) {
		
		// 출고구분
		String OutMatType = Integer.toString(18);
		model.addAttribute("OutMatType", dtl_Service.getAlldtl(OutMatType));
		// 부서명
		String OutMatDept = Integer.toString(3);
		model.addAttribute("OutMatDept", dtl_Service.getDeptName(OutMatDept));
		// 현재연월
		String PrcsDate = Integer.toString(20);
		String PrcsNum = Integer.toString(3);
		model.addAttribute("PrcsDate", dtl_Service.getDate(PrcsDate, PrcsNum));
		// 전월
		String LastMonth = Integer.toString(20);
		String LastNum = Integer.toString(1);
		model.addAttribute("LastMonth", dtl_Service.getDate(LastMonth, LastNum));
		// 마지막날
		String LastDay = Integer.toString(2);
		model.addAttribute("LastDay", dtl_Service.getLastDay(LastDay));
		
		model.addAttribute("pageName", "출고 현황");
		
		return "materialLX/matOutputLX/matOutputMasterLX";
	}
	
	// matInoutListLX
	@GetMapping("matInoutListLX")
	public String matInoutListLX(Model model) {
		model.addAttribute("pageName", "입출고 현황");
		return "materialLX/matInoutListLX";
	}
	
	// matStockMaster
	@GetMapping("matStockMasterLX")
	public String matStockMasterLX(Model model) {
		model.addAttribute("pageName", "현재고 현황");
		return "materialLX/matStockLX/matStockMasterLX";
	}
}
