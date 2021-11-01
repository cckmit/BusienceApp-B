package com.busience.system.service;

import java.util.List;

import com.busience.common.dto.DtlDto;
import com.busience.system.dto.MenuMgmtDto;

public interface MenuMgmtService {

	//상위 메뉴 list
	public List<DtlDto> menuMgmtParentList(String userCode);
	
	//메뉴 권한 list
	public List<MenuMgmtDto> menuMgmtList(String userCode);
	
	//메뉴 권한 update
	public int menuMgmtUpdate(List<MenuMgmtDto> jsonDataList);
}
