package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.CrateProductionDto;

@Mapper
public interface CrateProductionDao {

	//조회
	public List<CrateProductionDto> crateProductionSelectDao(SearchDto searchDto);
	
	//저장
	public int crateProductionSaveDao(CrateProductionDto crateProductionDto);
}
