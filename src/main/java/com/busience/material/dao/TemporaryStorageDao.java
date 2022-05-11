package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;

@Mapper
public interface TemporaryStorageDao {
	
	//자재 입고 등록
	public int temporaryStorageInsertDao(InMatDto inMatDto);
	
	//입고 리스트 조회
	public List<InMatDto> temporaryStorageSelectDao(SearchDto searchDto);
}