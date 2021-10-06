package com.busience.productionLX.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class productionLXController {
	
	//proResultLX
	@GetMapping("proResultLX")
	public String proResultLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");		
		return "productionLX/proResult";
	}
	
	//proItemSumLX
	@GetMapping("proItemSumLX")
	public String proItemSumLX(Model model) {
		model.addAttribute("pageName", "생산실적 관리(제품별)");
		return "productionLX/proItemSumLX";
	}

	//proMachineSumLX
	@GetMapping("proMachineSumLX")
	public String proMachineSumLX(Model model) {
		model.addAttribute("pageName", "생산실적 관리(설비별)");
		return "productionLX/proMachineSumLX";
	}
	
	//proItemListLX
	@GetMapping("proItemListLX")
	public String proItemListLX(Model model) {
		model.addAttribute("pageName", "생산실적 이력관리(제품별)");
		return "productionLX/proItemListLX";
	}
	
	//proMachineListLX
	@GetMapping("proMachineListLX")
	public String proMachineListLX(Model model) {
		model.addAttribute("pageName", "생산실적 이력관리(설비별)");
		return "productionLX/proMachineListLX";
	}
	
	//proSumMonth
	@GetMapping("proSumMonth")
	public String proSumMonth(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "productionLX/proSumMonth";
	}
	
	//proSumYear
	@GetMapping("proSumYear")
	public String proSumYear(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "productionLX/proSumYear";
	}
	
	//workorder
	@GetMapping("workorder")
	public String orderMaster(Model model) {
		
		model.addAttribute("pageName", "작업 지시");
		model.addAttribute("user_name", "관리자");
		
		return "productionLX/workorder";
	}
	
	//workorder_auto_data_send
	@GetMapping("workorder_auto_data_send")
	public String workorder_auto_data_send(Model model) {
		return "productionLX/workorder_auto_data_send";
	}
	
	//workorderList
	@GetMapping("workorderList")
	public String workorderList(Model model) {
		model.addAttribute("pageName", "작업지시 조회");
		return "productionLX/workorderList";
	}
	
	//workList
	@GetMapping("workList")
	public String workList(Model model) {
		model.addAttribute("pageName", "작업 현황");
		return "productionLX/workList";
	}
	
	//workdList
	@GetMapping("workdList")
	public String workdList(Model model) {
		model.addAttribute("pageName", "세부 작업 현황");
		return "productionLX/workdList";
	}
	
	//worktdList
	@GetMapping("worktdList")
	public String workList2(Model model) {
		model.addAttribute("pageName", "기간별 세부 작업 현황");
		return "productionLX/worktdList/worktdListMaster";
	}
}
