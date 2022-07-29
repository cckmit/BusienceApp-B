package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.RawMaterialDto;

@Mapper
public interface RawMaterialDao {

	//조회
	public List<RawMaterialDto> rawMaterialSelectDao(SearchDto searchDto);
	
	//Lot 이력 조회
	public List<RawMaterialDto> rawMaterialListMasterDao(SearchDto searchDto);
	
	//저장
	public int rawMaterialSaveDao(RawMaterialDto rawMaterialDto);
	
	//삭제
	public int rawMaterialDeleteDao(@Param("Production_LotNo") String Production_LotNo, @Param("Material_LotNo") String Material_LotNo);
	
	//삭제
	public int rawMaterialDelete2Dao(String crateCode);
}
