package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.TransDto;

@Mapper
public interface TransDao {
	
	//트랜스 번호
	public int transNoSelectDao(String T_LotNo);
	
	//트랜스 저장
	public int transInsertDao(
			@Param("T_No") int T_No, @Param("T_LotNo") String T_LotNo, @Param("T_ItemCode") String T_ItemCode,
			@Param("T_Qty") Double T_Qty, @Param("T_Before") String T_Before, @Param("T_After") String T_After,
			@Param("T_Classify") String T_Classify
			);
	
	//트랜스
	public List<TransDto> transSelectDao(SearchDto searchDto);
}
