package com.busience.qc.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.qc.dto.OQC_Inspect_Dto;

@Mapper
public interface OQCInspectDao {

	// 저장
	public int oqcInspectInsertDao(OQC_Inspect_Dto oqc_Inspect_Dto);
}
