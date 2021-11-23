package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.MenuDto;

@Mapper
public interface MenuAddDao {
	
	//코드 목록
	public List<MenuDto> menuAddListDao();
	
	//메뉴 저장
	public int menuAddInsertDao(MenuDto menuDto);
	
	//menuMgmt
	public int menuMgmtInsertDao(MenuDto menuDto);
	
	//rightsMgmt
	public int rightsMgmtInsertDao(MenuDto menuDto);
}
