package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.system.dto.MenuMgmtDto;

@Mapper
public interface MenuMgmtDao {
		
	//메뉴 관리 list
	public List<MenuMgmtDto> menuMgmtListDao(String userCode);
	
	//메뉴 관리 update
	public int menuMgmtUpdateDao(MenuMgmtDto menuMgmtDto);
		
}
