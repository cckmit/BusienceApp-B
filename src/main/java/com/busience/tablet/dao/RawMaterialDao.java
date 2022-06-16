package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.RawMaterialDto;

@Mapper
public interface RawMaterialDao {

	//조회
	public List<RawMaterialDto> rawMaterialSelectDao(SearchDto searchDto);
	
	//저장
	public int rawMaterialSaveDao(RawMaterialDto rawMaterialDto);
}
