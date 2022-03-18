package com.busience.common.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.DtlDto;
import com.busience.common.dto.IotCheckDto;
import com.busience.common.dto.MenuDto;
import com.busience.common.service.DtlService;
import com.busience.common.service.IotCheckService;
import com.busience.common.service.MenuService;
import com.busience.common.service.ProductionService;

@RestController
public class CommonRestController {
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	IotCheckService iotCheckService;
	
	@Autowired
	ProductionService productionService;
	
	// 공통코드 찾기 (All)
	@GetMapping("/dtl_tbl_select")
	public List<DtlDto> dtl_tbl_select(HttpServletRequest request) {
		return dtlService.getAllDtl(Integer.parseInt(request.getParameter("NEW_TBL_CODE")));
	}
	
	// 공통코드 찾기 (true)
	@GetMapping("/dtlTrueSelect")
	public List<DtlDto> dtlTrueSelect(DtlDto dtlDto) {
		return dtlService.getDtl(dtlDto.getNEW_TBL_CODE());
	}
	
	//하위 메뉴 List
	@GetMapping("/menuList")
	public List<MenuDto> menuList(Principal principal) {
		return menuService.menuList(principal.getName());
	}
	
	//카운트일시정지 기능
	@GetMapping("/tablet/pauseChange")
	public boolean pauseChange(boolean TF) {
		return productionService.pauseChange(TF);
	}
	
	//생산량 저장
	@GetMapping("/bsapp2")
	public int bsapp2(String equip, int value) {
		return productionService.insertProduction(equip, value);
	}
	
	//온도 저장(임시)
	@GetMapping("/bsapp_temp")
	public int bsapp_temp(String equip, float value) {
		return productionService.insertTemperature(equip, value);
	}

	@GetMapping("tablet/testCheck")
	public List<IotCheckDto> testCheck() {
		return iotCheckService.IotCheckList();
	}
}
