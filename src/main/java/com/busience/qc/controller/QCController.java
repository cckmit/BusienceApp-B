package com.busience.qc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class QCController {

	@Autowired
	DtlService dtlService;
	
	@GetMapping("/iqcList")
	public String iqcList(Model model) {
		model.addAttribute("itemInspectList", dtlService.getDtl(24));
		model.addAttribute("pageName", "자재 검사 실적");
		return "qc/iqcList";
	}
	
	@GetMapping("/iqcRate")
	public String iqcRate(Model model) {
		model.addAttribute("defectList", dtlService.getDtl(32));
		model.addAttribute("pageName", "자재별 불량율");
		return "qc/iqcRate";
	}
	
	@GetMapping("/oqcList")
	public String oqcList(Model model) {
		model.addAttribute("itemInspectList", dtlService.getDtl(24));
		model.addAttribute("pageName", "출하 검사 실적");
		return "qc/oqcList";
	}
	
	// itemPackingInspect
	@GetMapping("/itemPackingInspect")
	public String itemPackingInspect(Model model) {
		model.addAttribute("itemInspectList", dtlService.getDtl(24));
		model.addAttribute("pageName", "제품 포장 검사");
		return "qc/itemPackingInspect";
	}
	
	@GetMapping("itemPackingInspectPrint")
	public String itemPackingInspectPrint(HttpServletRequest request) {
		return "normal/qc/itemPackingInspectPrint";
	}

	// oqcOutputInspect
	@GetMapping("/oqcOutputInspect")
	public String oqcOutputInspect(Model model) {

		model.addAttribute("oqcInspectWorkerList", dtlService.getDtl(24));
		model.addAttribute("pageName", "출하 검사 관리");

		return "qc/oqcOutputInspect";
	}
	
	// oqcOutputInspectPrint
	@GetMapping("/oqcOutputInspectPrint")
	public String oqcOutputInspectPrint(Model model) {
		return "normal/qc/oqcOutputInspectPrint";
	}

	// oqcOutputList
	@GetMapping("oqcOutputList")
	public String oqcOutputList(Model model) {

		// 로직
		int rogic = 22;
		model.addAttribute("rogicList", dtlService.getDtl(rogic));

		model.addAttribute("pageName", "최종 검사(설비별)");

		return "qc/oqcOutputList";
	}

	// oqcOutputDefecterate
	@GetMapping("oqcOutputDefecterate")
	public String oqcOutputDefecterate(Model model) {

		// 로직
		int rogic = 22;
		model.addAttribute("rogicList", dtlService.getDtl(rogic));

		model.addAttribute("pageName", "제품별 불량율");

		return "qc/oqcOutputDefectrate/oqcMaster";
	}

	// defectInsert
	@GetMapping("defectInsert")
	public String defectInsert(Model model) {
		model.addAttribute("defectList", dtlService.getDtl(32));
		model.addAttribute("pageName", "출하 검사 불량률");
		return "qc/defectInsert";
	}

	// defectList
	@GetMapping("defectList")
	public String defectList(Model model) {
		model.addAttribute("pageName", "검사 이력 관리");
		return "qc/defectList";
	}

	// defectInsertM
	@GetMapping("defectItemList")
	public String defectItemList(Model model) {
		model.addAttribute("pageName", "검사 이력 관리(제품별)");
		return "qc/defectItemList";
	}

	// defectInsertM
	@GetMapping("defectMachineList")
	public String defectMachineList(Model model) {
		model.addAttribute("pageName", "검사 이력 관리(설비별)");
		return "qc/defectMachineList";
	}

	// defectInsertM
	@GetMapping("defectInsertM")
	public String defectInsertM(Model model) {
		return "normal/qc/defectInsertM";
	}
}
