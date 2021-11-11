package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.LogApiDataDto;

@Mapper
public interface LogApiDataDao {
	
	public int LogDataInsertDao(LogApiDataDto logApiDataDto);
	
	public List<LogApiDataDto> LogDataSelectDao(String sysUser);

}
