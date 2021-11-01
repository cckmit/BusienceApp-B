package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.DtlDto;
import com.busience.system.dto.UserMenuDto;

@Mapper
public interface UserMenuDao {
	
	//사용자 메뉴 목록
	public List<UserMenuDto> userMenuListDao(String User_Code);
	
	//사용자 메뉴 저장
	public int userMenuInsertDao(UserMenuDto userMenuDto);
	
	//사용자 메뉴 삭제
	public int userMenuDeleteDao(UserMenuDto userMenuDto);
}
