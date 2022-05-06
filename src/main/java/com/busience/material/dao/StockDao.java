package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.StockDto;

@Mapper
public interface StockDao {
	
	//재고테이블 조회
	public List<StockDto> stockSelectDao(SearchDto searchDto);
	
	//영업 재고테이블 조회 
	public List<StockDto> salesStockSelectDao(SearchDto searchDto);
	
	//출고지시수량 재고 확인
	public List<StockDto> salesOutputStockDao(SearchDto searchDto);
	
	//재고테이블 저장
	public int stockInsertUpdateDao(
			@Param("S_ItemCode") String S_ItemCode, @Param("S_Qty") Double S_Qty, @Param("S_WareHouse") String S_WareHouse
			);
	
	//재고테이블 업데이트
	public int stockUpdateDao(@Param("LM_Qty") Double LM_Qty, @Param("LM_ItemCode") String LM_ItemCode, @Param("LT_Before") String LT_Before);
	
	//재고테이블 저장
	public int stockInsertDao(@Param("LM_ItemCode") String LM_ItemCode, @Param("LM_Qty") Double LM_Qty, @Param("LM_WareHouse") String LM_Warehouse);
	
	//출고지시 조회 재고 확인
	public List<StockDto> salesOutputOrderStockDao(SearchDto searchDto);
	
	//영업 반품 업데이트
	public int stockReturnUpdateDao(@Param("LM_Qty") Double LM_Qty, @Param("LM_ItemCode") String LM_ItemCode, @Param("LT_Before") String LT_Before);
}
