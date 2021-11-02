package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.system.dto.MenuDto;

@Mapper
public interface MenuDao {

	//유저별 메뉴 list
	public List<MenuDto> menuListDao(String User_Code);
}
