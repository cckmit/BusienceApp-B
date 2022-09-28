package com.busience.material.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;
import com.busience.material.service.MatOrderService;
import com.busience.standard.dto.UserDto;
import com.busience.standard.service.CustomerService;
import com.busience.standard.service.UserService;

@Controller
public class materialController {
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	MatOrderService matOrderService; 
	
	//OrderMaster
	@GetMapping("matOrder")
	public String matOrder(Model model) {
		model.addAttribute("pageName", "발주 관리");
		return "material/matOrder";
	}
	
	//OrderMaster
	@GetMapping("matOrderList")
	public String matOrderList(Model model) {
		model.addAttribute("pageName", "발주 조회");
		return "material/matOrderList";
	}
	
	@GetMapping("matOrderPrint")
	public String orderPrint(String orderNo, String clientCode, Model model) {
		model.addAttribute("orderMaster", matOrderService.orderMasterOneSelect(orderNo));
		model.addAttribute("myCom", customerService.selectCustomer("C09999"));
		model.addAttribute("client", customerService.selectCustomer(clientCode));
		return "normal/material/matOrderPrint";
	}
	
	//OrderMaster
	@GetMapping("matRequest")
	public String matRequest(Model model, Principal principal) {
		UserDto userDto = userService.selectUser(principal.getName());
		
		model.addAttribute("userInfo" ,userDto);
		model.addAttribute("pageName", "자재 요청");
		return "material/matRequest";
	}
	
	//OrderMaster
	@GetMapping("matRequestList")
	public String matRequestList(Model model, Principal principal) {
		UserDto userDto = userService.selectUser(principal.getName());
		
		model.addAttribute("userInfo" ,userDto);
		model.addAttribute("pageName", "자재 요청 조회");
		return "material/matRequestList";
	}
	
	// MatInput
	@GetMapping("matTempInput")
	public String matTempInput(Model model) {
		model.addAttribute("pageName", "가입고 관리");
		return "material/matInput";
	}
	
	// MatInput
	@GetMapping("matInput")
	public String matInput(Model model) {
		model.addAttribute("pageName", "입고 관리");
		return "material/matInput";
	}
	
	// MatInputInspection
	@GetMapping("matInputInspection")
	public String MatInputInspection(Model model) {
		model.addAttribute("matInputInspectList", dtlService.getDtl(44));
		model.addAttribute("pageName", "입고 관리 검사");
		return "material/matInputInspection";
	}
	
	@GetMapping("matInputInspectionPrint")
	public String InputInspectionPrint(HttpServletRequest request) {
		return "normal/material/matInputInspectionPrint";
	}
	// MatOutput
	@GetMapping("matOutput")
	public String matOutput(Model model) {
		
		// 부서명
		int deptList = 3;
		model.addAttribute("deptList", dtlService.getDtl(deptList));
		
		model.addAttribute("pageName", "출고 관리");
		
		if(dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS()) {
			return "material/matOutput";
		}else {
			return "material/matOutputLX";
		}
	}
	
	// matInReturn
	@GetMapping("matInReturn")
	public String orderMaster(Model model) {
		model.addAttribute("pageName", "입고 반품 관리");
		
		if(dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS()) {
			return "material/matInReturn/matInReturnMaster";
		}else {
			return "material/matInReturn/matInReturnLXMaster";
		}
	}
	
	//matInReturn
	@GetMapping("matOutReturn")
	public String matOutReturn(Model model) {
		model.addAttribute("stockList", dtlService.getDtl(10));
		model.addAttribute("pageName", "출고 반품 관리");
		return "material/matOutReturn/matOutReturnMaster";
	}
	
	//matInReturn
	@GetMapping("matTrans")
	public String matTrans(Model model) {
		model.addAttribute("pageName", "자재 이동 관리");
		return "material/matTrans/matTransMaster";
	}
	
	// matInputMaster
	@GetMapping("matTempInputMaster")
	public String matTempInputMaster(Model model) {
			
		// 입고구분
		int InMatType = 17;
		model.addAttribute("InMatType", dtlService.getDtl(InMatType));
		
		// 마지막날
		int LastDay = 2;
		model.addAttribute("LastDay", dtlService.getLastDay(LastDay));
		
		// 현재연월
		int PrcsDate = 20;
		String PrcsNum = Integer.toString(3);
		model.addAttribute("PrcsDate", dtlService.getDate(PrcsDate, PrcsNum));
		
		// 전월
		int LastMonth = 20;
		String LastNum = Integer.toString(1);
		model.addAttribute("LastMonth", dtlService.getDate(LastMonth, LastNum));
		
		// 메뉴명
		model.addAttribute("pageName", "가입고 현황");
				
		return "material/matInput/matInputMaster";
	}
	
	// matInputMaster
	@GetMapping("matInputMaster")
	public String matInputMaster(Model model) {
			
		// 입고구분
		int InMatType = 17;
		model.addAttribute("InMatType", dtlService.getDtl(InMatType));
		
		// 마지막날
		int LastDay = 2;
		model.addAttribute("LastDay", dtlService.getLastDay(LastDay));
		
		// 현재연월
		int PrcsDate = 20;
		String PrcsNum = Integer.toString(3);
		model.addAttribute("PrcsDate", dtlService.getDate(PrcsDate, PrcsNum));
		
		// 전월
		int LastMonth = 20;
		String LastNum = Integer.toString(1);
		model.addAttribute("LastMonth", dtlService.getDate(LastMonth, LastNum));
		
		// 메뉴명
		model.addAttribute("pageName", "입고 현황");
				
		return "material/matInput/matInputMaster";
	}
	
	// matOutputMaster
	@GetMapping("matOutputMaster")
	public String matOutputMaster(Model model) {
		
		// 출고구분
		int OutMatType = 18;
		model.addAttribute("OutMatType", dtlService.getDtl(OutMatType));
		
		// 부서명
		int OutMatDept = 3;
		model.addAttribute("OutMatDept", dtlService.getDeptName(OutMatDept));
		
		// 현재연월
		int PrcsDate = 20;
		String PrcsNum = Integer.toString(3);
		model.addAttribute("PrcsDate", dtlService.getDate(PrcsDate, PrcsNum));
		
		// 전월
		int LastMonth = 20;
		String LastNum = Integer.toString(1);
		model.addAttribute("LastMonth", dtlService.getDate(LastMonth, LastNum));
		
		// 마지막날
		int LastDay = 2;
		model.addAttribute("LastDay", dtlService.getLastDay(LastDay));
		
		// 메뉴명
		model.addAttribute("pageName", "출고 현황");
		
		return "material/matOutput/matOutputMaster";
	}
	
	// matInoutList
	@GetMapping("matInoutList")
	public String matInoutList(Model model) {
		model.addAttribute("pageName", "입출고 현황");
		return "material/matInoutList";
	}
	
	// matStockMaster
	@GetMapping("matStockMaster")
	public String matStockMaster(Model model) {
		model.addAttribute("pageName", "현재고 현황");
		model.addAttribute("lotUse", dtlService.getAllDtl(31).get(2).isCHILD_TBL_USE_STATUS());
		return "material/matStock/matStockMaster";
	}
	
	//matOutputBoard
	@GetMapping("tablet/matOutputBoard")
	public String matOutputTablet(Model model) {
		return "normal/material/matOutputBoard";
	}
	
	//matStockChangeMaster
	@GetMapping("matStockChangeMaster")
	public String matStockChangeMaster(Model model) {
		model.addAttribute("stockList", dtlService.getDtl(10));
		model.addAttribute("pageName", "재고 조정");
		return "material/matStockChangeMaster";
	}
	
}
