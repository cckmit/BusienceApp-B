package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockMat_tbl;

@Mapper
public interface MatOutputDao {
	
	//LotMaster 조회
	public List<LotMasterDto> LotMasterSelectDao(SearchDto searchDto);
	
	//조회
	public List<StockMat_tbl> outMatListDao();
	
	//입고테이블 등록
	public int outMatInsertDao(OutMatDto outMatDto);
	
	//재고테이블 수정
	public int stockUpdateDao(OutMatDto outMatDto);
}
