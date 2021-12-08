package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.TestCheckDto;

@Mapper
public interface TestCheckDao {
	
	public List<TestCheckDto> TestCheckListDao();
	
	public int TestInsertDao(TestCheckDto testCheckDto);
	
}
