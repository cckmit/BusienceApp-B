package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.SalesOrderListDto;
import com.busience.sales.dto.SalesOutputOrderListDto;

@Mapper
public interface SalesOrderListDao {

	// SalesOrderList select
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto);

	// SalesOrderList insert
	public int salesOrderListInsertUpdateDao(SalesOrderListDto salesOrderListDto);

	// SalesOrderList delete
	public int salesOrderListDeleteDao(@Param("orderNo") String orderNo, @Param("list") List<SalesOrderListDto> salesOrderListDto);
	
	// salesOrderList update
	public int salesOrderListUpdateDao(SalesOutputOrderListDto salesOutputOrderListDto);
}
