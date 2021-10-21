package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;

@Controller
public class productionLXController {

	@Autowired
	JdbcTemplate jdbctemplate;

	// proResultLX
	@GetMapping("proResultLX")
	public String proResultLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");
		return "productionLX/proResult";
	}

	// proResultLXX
	@GetMapping("proResultLXX")
	public String proResultLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");
		return "productionLX/proResultX";
	}

	// proItemSumLX
	@GetMapping("proItemSumLX")
	public String proItemSumLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(제품별)");
		return "productionLX/proItemSumLX";
	}

	// proItemSumLXX
	@GetMapping("proItemSumLXX")
	public String proItemSumLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(제품별)");
		return "productionLX/proItemSumLXX";
	}

	// proMachineSumLX
	@GetMapping("proMachineSumLX")
	public String proMachineSumLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(설비별)");
		return "productionLX/proMachineSumLX";
	}

	// proMachineSumLXX
	@GetMapping("proMachineSumLXX")
	public String proMachineSumLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(설비별)");
		return "productionLX/proMachineSumLXX";
	}

	// proItemListLX
	@GetMapping("proItemListLX")
	public String proItemListLX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(제품별)");
		return "productionLX/proItemListLX";
	}

	// proItemListLXX
	@GetMapping("proItemListLXX")
	public String proItemListLXX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(제품별)");
		return "productionLX/proItemListLXX";
	}

	// proMachineListLX
	@GetMapping("proMachineListLX")
	public String proMachineListLX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(설비별)");
		return "productionLX/proMachineListLX";
	}

	// proMachineListLXX
	@GetMapping("proMachineListLXX")
	public String proMachineListLXX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(설비별)");
		return "productionLX/proMachineListLXX";
	}

	// proSumMonth
	@GetMapping("proSumMonth")
	public String proSumMonth(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "productionLX/proSumMonth";
	}

	// proSumMonth2
	@GetMapping("proSumMonthX")
	public String proSumMonth2(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "productionLX/proSumMonthX";
	}

	// proSumYear
	@GetMapping("proSumYear")
	public String proSumYear(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "productionLX/proSumYear";
	}

	// proSumYear
	@GetMapping("proSumYearX")
	public String proSumYearX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "productionLX/proSumYearX";
	}

	// workorder
	@GetMapping("workorder")
	public String orderMaster(Model model) {

		model.addAttribute("pageName", "작업 지시");

		return "productionLX/workorder";
	}

	// workorderList
	@GetMapping("workorderList")
	public String workorderList(Model model) {
		model.addAttribute("pageName", "작업지시 조회");
		return "productionLX/workorderList";
	}

	// workList
	@GetMapping("workList")
	public String workList(Model model) {
		model.addAttribute("pageName", "작업 현황");
		return "productionLX/workList";
	}

	// workdList
	@GetMapping("workdList")
	public String workdList(Model model) {
		model.addAttribute("pageName", "세부 작업 현황");
		return "productionLX/workdList";
	}

	// worktdList
	@GetMapping("worktdList")
	public String workList2(Model model) {
		model.addAttribute("pageName", "기간별 세부 작업 현황");
		return "productionLX/worktdList/worktdListMaster";
	}

	// WorkOrderTAB
	@GetMapping("WorkOrderTAB")
	public String WorkOrderTAB(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("list",
				jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
					@Override
					public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
						data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
						data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
						return data;
					}
				}));

		model.addAttribute("list2",
				jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29'", new RowMapper<DTL_TBL>() {
					@Override
					public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						DTL_TBL data = new DTL_TBL();
						data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
						data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
						return data;
					}
				}));

		return "normal/productionLX/work_order_TAB";
	}

	// WorkOrderTAB
	@GetMapping("WorkOrderTABX")
	public String WorkOrderTABX(Model model, HttpServletRequest request) throws SQLException {
		return "normal/productionLX/work_order_TABX";
	}
}
