package com.busience.system.service;

import java.util.List;

import com.busience.system.dto.RightsMgmtDto;

public interface RightsMgmtService {

	//메뉴 권한 list
	public List<RightsMgmtDto> rightsMgmtList(String userType);
	
	//메뉴 권한 update
	public int rightsMgmtUpdate(List<RightsMgmtDto> jsonDataList);
}
