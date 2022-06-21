package com.busience.production.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;
import com.busience.standard.service.MachineService;

@Controller
public class productionController {

	@Autowired
	MachineService machineService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Autowired
	DtlService dtlService;
	
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
	
	// proMaskSum
	@GetMapping("proMaskSum")
	public String proMaskSum(Model model) {
		model.addAttribute("pageName", "마스크 실적 현황");
		return "production/proMaskSum";
	}
	
	// proPackingSum
	@GetMapping("proPackingSum")
	public String proPackingSum(Model model) {
		model.addAttribute("pageName", "생산 포장 현황");
		return "production/proPackingSum";
	}
	
	// matInputSum
	@GetMapping("matInputSum")
	public String matInputSum(Model model) {
		model.addAttribute("pageName", "자재 투입 현황");
		return "production/matInputSum";
	}
	
	// itemPackingList
	@GetMapping("itemPackingList")
	public String itemPackingList(Model model) {
		model.addAttribute("pageName", "제품 생산 현황(대포장)");
		return "production/itemPackingList";
	}
	
	// lotIssueMaster
	@GetMapping("lotIssueMaster")
	public String lotIssueMaster(Model model) {
		// 설비 종류 리스트 가져오기
		model.addAttribute("equipTypeList", dtlService.getDtl(43));
		model.addAttribute("pageName", "Lot 발행 조회");
		return "production/lotIssueMaster";
	}
	
	// lotListMaster
	@GetMapping("lotListMaster")
	public String lotListMaster(Model model) {
		model.addAttribute("pageName", "Lot 이력 조회");
		return "production/lotListMaster";
	}
	
	// workorder
	@GetMapping("workorder")
	public String workorder(Model model) {
		model.addAttribute("pageName", "설비별 작업지시");
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
	
	@GetMapping("/processInspection")
	public String processInspection(Model model) {
		model.addAttribute("processInspectList", dtlService.getDtl(44));
		model.addAttribute("pageName", "공정 검사 관리");
		return "/production/processInspection";
	}
	
	@GetMapping("processInspectPrint")
	public String processInspectionPrint(HttpServletRequest request) {
		return "normal/production/processInspectPrint";
	}
	
	@GetMapping("/crateManage")
	public String crateManage(Model model) {
		model.addAttribute("pageName", "Crate 바코드 관리");
		return "/production/crateManage";
	}
}
