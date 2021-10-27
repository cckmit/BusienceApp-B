package com.busience.system.service;

import java.util.List;

import com.busience.system.dto.MenuMgmtDto;

public interface MenuMgmtService {

	//메뉴 권한 list
	public List<MenuMgmtDto> menuMgmtList(String userCode);
	
	//메뉴 권한 update
	public int menuMgmtUpdate(List<MenuMgmtDto> jsonDataList);
}
