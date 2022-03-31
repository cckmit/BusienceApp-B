package com.busience.material.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;
import com.busience.standard.dto.UserDto;
import com.busience.standard.service.UserService;

@Controller
public class materialLXController {
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	UserService userService;
	
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
	
	//OrderMaster
	@GetMapping("matRequest")
	public String matRequest(Model model, Principal principal) {
		UserDto userDto = userService.selectUser(principal.getName());
		
		model.addAttribute("userInfo" ,userDto);
		model.addAttribute("pageName", "자재 요청");
		return "materialLX/matRequest";
	}
	
	//OrderMaster
	@GetMapping("matRequestList")
	public String matRequestList(Model model, Principal principal) {
		UserDto userDto = userService.selectUser(principal.getName());
		
		model.addAttribute("userInfo" ,userDto);
		model.addAttribute("pageName", "자재 요청 조회");
		return "materialLX/matRequestList";
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
		
		// 부서명
		int deptList = 3;
		model.addAttribute("deptList", dtlService.getDtl(deptList));
		
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
	public String matInputMasterLX(Model model) {
			
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
				
		return "materialLX/matInputLX/matInputMasterLX";
	}
	
	// matOutputMasterLX
	@GetMapping("matOutputMasterLX")
	public String matOutputMasterLX(Model model) {
		
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
		return "materialLX/matStockMasterLX";
	}
	
	//matOutputLXBoard
	@GetMapping("tablet/matOutputLXBoard")
	public String matOutputTablet(Model model) {
		
		return "normal/materialLX/matOutputLXBoard";
	}
}
