package com.busience.system.service;

import java.util.List;

import com.busience.system.dto.UserMenuDto;

public interface UserMenuService {

	//사용자 메뉴 리스트
	public List<UserMenuDto> userMenuList(String User_Code);
	
	//사용자 메뉴 저장
	public int userMenuInsert(List<UserMenuDto> UserMenuDtoList, String User_Code);
	
	//사용자 메뉴 삭제
	public int userMenuDelete(List<UserMenuDto> UserMenuDtoList);
}
