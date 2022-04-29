package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.RawMaterialSubDto;

@Mapper
public interface RawMaterialSubDao {

	//조회
	public List<RawMaterialSubDto> rawMaterialSubSelectDao(SearchDto searchDto);
	
	//저장
	public int rawMaterialSubSaveDao(RawMaterialSubDto rawMaterialDto);
}
