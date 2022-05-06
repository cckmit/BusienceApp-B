package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotTransDto;

@Mapper
public interface LotTransDao {
	
	//랏트랜스 번호
	public int lotTransNoSelectDao(String LT_LotNo);
	
	//랏트랜스 저장
	public int lotTransInsertDao(
			@Param("LT_No") int LT_No, @Param("LT_LotNo") String LT_LotNo, @Param("LT_ItemCode") String LT_ItemCode,
			@Param("LT_Qty") Double LT_Qty, @Param("LT_Before") String LT_Before, @Param("LT_After") String LT_After,
			@Param("LT_Classify") String LT_Classify
			);
	
	//입출고 조회
	public List<LotTransDto> inOutMatSelectDao(SearchDto searchDto);
	
	//랏트랜스 번호
	public int lotTransNoSelectDao2(String LT_LotNo);
	
	//랏트랜스 저장
	public int lotTransInsertDao2(
		@Param("LT_No") int LT_No, @Param("LT_LotNo") String LT_LotNo, @Param("LT_ItemCode") String LT_ItemCode,
		@Param("LT_Qty") Double LT_Qty, @Param("LT_Before") String LT_Before, @Param("LT_After") String LT_After,
		@Param("LT_Send_Clsfc") String LT_Send_Clsfc
	);
}
