package com.busience.common.service;

import java.util.List;

import com.busience.common.dto.MenuDto;

public interface MenuService {

	//유저별 메뉴 list
	public List<MenuDto> menuList(String User_Code);
}
