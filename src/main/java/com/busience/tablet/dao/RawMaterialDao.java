package com.busience.tablet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.tablet.dto.RawMaterialDto;

@Mapper
public interface RawMaterialDao {

	//조회
	//public List<RawMaterialSubDto> rawMaterialSubSelectDao(SearchDto searchDto);
	
	//저장
	public int rawMaterialSaveDao(RawMaterialDto rawMaterialDto);
}
