package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.system.dto.MenuDto;

@Mapper
public interface MenuDao {
	
	//메뉴 목록
	public List<MenuDto> listDao();
}
