package com.busience.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.system.dto.CmnDto;

@Mapper
public interface CmnDao {
	
	//코드 목록
	public List<CmnDto> cmnListDao();
	
	//사용자타입 저장시 메뉴 저장
	public int cmnInsertDao(String userType);
}
