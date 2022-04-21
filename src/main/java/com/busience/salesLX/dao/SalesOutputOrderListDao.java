package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOutputOrderListDto;

@Mapper
public interface SalesOutputOrderListDao {
	
	// salesOutputOrderList select
	public List<SalesOutputOrderListDto> salesOutputOrderListSelectDao(SearchDto searchDto);
	
	// salesOutputOrderList insert
	public int salesOutputOrderListInsertDao(SalesOutputOrderListDto salesOutputOrderListDto);
}
