package com.busience.production.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.standard.service.MachineService;

@Controller
public class productionController {

	@Autowired
	MachineService machineService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	// proResult
	@GetMapping("proResult")
	public String proResult(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");
		return "production/proResult";
	}

	// proItemSum
	@GetMapping("proItemSum")
	public String proItemSum(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(제품별)");
		return "production/proItemSum";
	}

	// proMachineSum
	@GetMapping("proMachineSum")
	public String proMachineSum(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(설비별)");
		return "production/proMachineSum";
	}

	// proItemList
	@GetMapping("proItemList")
	public String proItemList(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(제품별)");
		return "production/proItemList";
	}

	// proMachineList
	@GetMapping("proMachineList")
	public String proMachineList(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(설비별)");
		return "production/proMachineList";
	}

	// proSumMonth
	@GetMapping("proSumMonth")
	public String proSumMonth(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "production/proSumMonth";
	}

	// proSumYear
	@GetMapping("proSumYear")
	public String proSumYear(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "production/proSumYear";
	}

	// proComparedInput
	@GetMapping("proCalendar")
	public String proComparedInput(Model model) {
		model.addAttribute("pageName", "투입 대비 생산 실적");
		return "production/proCalendar";
	}
	
	// workorder
	@GetMapping("workorder")
	public String workorder(Model model) {
		model.addAttribute("pageName", "작업지시 등록");
		return "production/workorder";
	}
	
	// workorder
	@GetMapping("workOrder2")
	public String workOrder2(Model model) {
		model.addAttribute("pageName", "작업지시 등록2");
		return "production/workOrder2";
	}
	
	// workorderList
	@GetMapping("workorderList")
	public String workorderList(Model model) {
		model.addAttribute("pageName", "작업지시 접수");
		return "production/workorderList";
	}

	// workList
	@GetMapping("workList")
	public String workList(Model model) {
		model.addAttribute("pageName", "작업 현황");
		
		return "production/workList";
	}

	// workdList
	@GetMapping("workdList")
	public String workdList(Model model) {
		model.addAttribute("pageName", "세부 작업 현황");
		return "production/workdList";
	}

	// worktdList
	@GetMapping("worktdList")
	public String workList2(Model model) {
		model.addAttribute("pageName", "기간별 세부 작업 현황");
		return "production/worktdList/worktdListMaster";
	}

	@GetMapping("/tempDaily")
	public String tempDaily(Model model, HttpServletRequest request) throws SQLException{
		
		// 설비 리스트 가져오기
		model.addAttribute("machineList", machineService.selectMachineList());
		
		model.addAttribute("pageName", "온도 조회");
		return "/production/tempDaily";
	}
	
	@GetMapping("/crateManage")
	public String crateManage(Model model) {
		model.addAttribute("pageName", "상자 관리");
		return "/production/crateManage";
	}
}
