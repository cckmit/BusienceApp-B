package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockDto;

@Mapper
public interface StockDao {
	
	//재고테이블 조회
	public List<StockDto> stockSelectDao(SearchDto searchDto);
	
	//출고지시수량 재고 확인
	public List<StockDto> salesOutputStockDao(SearchDto searchDto);
	
	//재고테이블 저장
	public int stockInsertUpdateDao(InMatDto inMatDto);
	
	//재고테이블 업데이트
	public int stockUpdateDao(OutMatDto outMatDto);
	
	//재고테이블 저장
	public int stockInsertDao(OutMatDto outMatDto);
	
	//출고지시 조회 재고 확인
	public List<StockDto> salesOutputOrderStockDao(SearchDto searchDto);
}
