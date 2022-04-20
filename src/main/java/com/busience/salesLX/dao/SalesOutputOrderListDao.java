package com.busience.salesLX.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.salesLX.dto.SalesOutputOrderListDto;

@Mapper
public interface SalesOutputOrderListDao {
	
	// salesOutputOrderList insert
	public int salesOutputOrderListInsertDao(SalesOutputOrderListDto salesOutputOrderListDto);
}
