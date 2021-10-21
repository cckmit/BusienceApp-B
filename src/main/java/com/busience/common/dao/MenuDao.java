package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.common.dto.MenuDto;

@Mapper
public interface MenuDao {
	
	//메뉴 목록
	public List<MenuDto> listDao();
	
}
