package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.material.dto.OutMat_tbl;
import com.busience.material.dto.StockMat_tbl;

@Mapper
public interface MatOutputDao {
	
	//조회
	public List<StockMat_tbl> outMatListDao();
	
	//입고테이블 등록
	public int outMatInsertDao(OutMat_tbl outMat_tbl);
	
	//재고테이블 수정
	public int stockMatUpdateDao(OutMat_tbl outMat_tbl);
}
