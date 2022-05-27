package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProcessInspectDto;

@Mapper
public interface ProcessInspectDao {
	
	// 검사 리스트 조회
	public List<ProcessInspectDto> processInspectListDao(SearchDto searchDto);
	
	// 검사 form 조회
	public String processInspectOneSelectDao(SearchDto searchDto);

	public int processInspectInsertDao(ProcessInspectDto processInspectDto);
}
