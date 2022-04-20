package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOrderListDto;
import com.busience.salesLX.dto.SalesOutputOrderListDto;

@Mapper
public interface SalesOrderListDao {

	// SalesOrderList select
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto);

	// SalesOrderList insert
	public int salesOrderListInsertUpdateDao(SalesOrderListDto salesOrderListDto);

	// SalesOrderList delete
	public int salesOrderListDeleteDao(@Param("list") List<SalesOrderListDto> salesOrderListDto,
									   @Param("Sales_Order_lCus_No") String Sales_Order_lCus_No);
	// salesOrderNoUpdate
	public int salesOrderListNoUpdateDao(String Sales_Order_lCus_No);
	
	// salesOrderList update
	public int salesOrderListUpdateDao(SalesOutputOrderListDto salesOutputOrderListDto);
}
