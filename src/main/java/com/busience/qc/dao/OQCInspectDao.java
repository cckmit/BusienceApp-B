package com.busience.qc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.OQC_Inspect_Dto;

@Mapper
public interface OQCInspectDao {

	// oqcInspectList 
	public List<OQC_Inspect_Dto> oqcInspectList(SearchDto searchDto);
	
	// oqcInspectOneSelect
	public List<OQC_Inspect_Dto> oqcInspectOneSelect(SearchDto searchDto);
	
	// 저장
	public int oqcInspectInsertDao(OQC_Inspect_Dto oqc_Inspect_Dto);
}
