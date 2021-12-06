package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.salesLX.dto.Sales_InMat_tbl;
import com.busience.standard.dto.ItemDto;

@Mapper
public interface SalesInputLXDao {
	
	//조회
	public List<ItemDto> salesInputListDao();
	
	//입고테이블 등록
	public int salesInputInsertDao(Sales_InMat_tbl sales_InMat_tbl);
	
	//재고테이블 수정
	public int salesInputUpdateDao(Sales_InMat_tbl sales_InMat_tbl);
}
