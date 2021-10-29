package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.system.dto.RightsMgmtDto;

@Mapper
public interface RightsMgmtDao {
	
	//권한 관리 list
	public List<RightsMgmtDto> rightsMgmtListDao(String userType);
	
	//권한 관리 update
	public int rightsMgmtUpdateDao(RightsMgmtDto rightsMgmtDto);
		
}
