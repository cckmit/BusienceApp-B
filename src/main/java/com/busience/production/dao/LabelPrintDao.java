package com.busience.production.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.production.dto.LabelPrintDto;

@Mapper
public interface LabelPrintDao {
	
	// 자재 라벨 출력
	public LabelPrintDto rawMaterialLabelSelectDao(@Param("LotNo") String LotNo, @Param("Warehouse") String Warehouse);
	
	// 소포장 라벨 출력
	public LabelPrintDto smallPackagingLabelSelectDao(String Small_Packaging_LotNo);
	
	// 소포장 라벨 출력
	public LabelPrintDto largePackagingLabelSelectDao(String Sales_Large_Packing_LotNo);
	
}
