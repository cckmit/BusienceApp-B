package com.busience.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DTL_Service;

@Controller
public class standardController {
	
	private DTL_Service dtl_Service;
	
	public standardController(DTL_Service dtl_Service) {
		this.dtl_Service = dtl_Service;
	}
	
	@GetMapping("/userManage")
	public String userManage(Model model) {
		
		//사용자 타입
		String userType = Integer.toString(1);
		model.addAttribute("userTypeList", dtl_Service.getAlldtl(userType));
		
		//사업장
		String company = Integer.toString(2);
		model.addAttribute("companyList", dtl_Service.getAlldtl(company));
		
		//부서
		String dept = Integer.toString(3);
		model.addAttribute("deptList", dtl_Service.getAlldtl(dept));
		
		//메뉴명
		model.addAttribute("pageName", "사용자 관리");

		return "standard/userManage";
	}
	
	@GetMapping("itemManage")
	public String itemManage(Model model) {

		//사업장
		String company = Integer.toString(2);
		model.addAttribute("companyList", dtl_Service.getAlldtl(company));
		
		//단위
		String unit = Integer.toString(4);
		model.addAttribute("unitList", dtl_Service.getAlldtl(unit));
		
		//자재분류
		String mtrlClsfc = Integer.toString(5);
		model.addAttribute("mtrlClsfcList", dtl_Service.getAlldtl(mtrlClsfc));
	
		//품목분류1
		String itemClsfc1 = Integer.toString(6);
		model.addAttribute("itemClsfc1List", dtl_Service.getAlldtl(itemClsfc1));
	
		//품목분류2
		String itemClsfc2 = Integer.toString(7);
		model.addAttribute("itemClsfc2List", dtl_Service.getAlldtl(itemClsfc2));
		
		//재질
		String material = Integer.toString(8);
		model.addAttribute("materialList", dtl_Service.getAlldtl(material));
		
		//품목상태
		String itemStatus = Integer.toString(9);
		model.addAttribute("itemStatusList", dtl_Service.getAlldtl(itemStatus));
		
		//기본창고
		String basicWarehouse = Integer.toString(10);
		model.addAttribute("basicWarehouseList", dtl_Service.getAlldtl(basicWarehouse));

		//페이지명
		model.addAttribute("pageName", "품목 정보 관리");
		
		return "standard/itemManage";
	}
	
	@GetMapping("customerManage")
	public String customer(Model model){

		// 납품조건
		String customer = Integer.toString(15);
		model.addAttribute("customerList", dtl_Service.getAlldtl(customer));
		
		// 페이지명
		model.addAttribute("pageName", "거래처 관리");
		
		return "standard/customerManage";
	}
	
	@GetMapping("BOM")
	public String BOM(Model model) {
		
		// 자재분류
		String mtrlClsfc = Integer.toString(5);
		model.addAttribute("mtrlClsfcList", dtl_Service.getAlldtl(mtrlClsfc));
		model.addAttribute("pageName", "BOM");
		
		return "standard/BOM";
	}
	
	@GetMapping("BOMListMaster")
	public String BOMListMaster(Model model) {
		
		// 자재분류
		String mtrlClsfc = Integer.toString(5);
		model.addAttribute("mtrlClsfcList", dtl_Service.getAlldtl(mtrlClsfc));
		model.addAttribute("pageName", "BOM 조회");
		
		return "standard/BOMList/BOMListMaster";
	}
}
