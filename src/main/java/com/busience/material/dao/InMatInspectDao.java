package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatInspectDto;

@Mapper
public interface InMatInspectDao {
	
	// 선택 조회
	public List<InMatInspectDto> matInspectOneSelectDao(SearchDto searchDto);
	
	// 저장
	public int InMatInspectInsertDao(InMatInspectDto inMatInspectDto);
}
