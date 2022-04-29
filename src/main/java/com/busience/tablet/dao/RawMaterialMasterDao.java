package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.RawMaterialMasterDto;

@Mapper
public interface RawMaterialMasterDao {

	//조회
	public List<RawMaterialMasterDto> rawMaterialMasterSelectDao(SearchDto searchDto);
	
	//조회
	public List<RawMaterialMasterDto> rawMaterialRecordSelectDao(SearchDto searchDto);
	
	//LotNo 순번
	public String rawMaterialLotNoSelectDao(RawMaterialMasterDto rawMaterialMasterDto);
	
	//저장
	public int rawMaterialMasterSaveDao(RawMaterialMasterDto rawMaterialMasterDto);
	
	//상태 업데이트
	public int rawMaterialMasterUpdateDao(RawMaterialMasterDto rawMaterialMasterDto);
	
	//수량 업데이트
	public int rawMaterialQtyUpdateDao(RawMaterialMasterDto rawMaterialMasterDto);
}
