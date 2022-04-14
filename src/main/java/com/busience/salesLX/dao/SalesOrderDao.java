package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOrderListDto;
import com.busience.salesLX.dto.SalesOrderMasterDto;

@Mapper
public interface SalesOrderDao {

	// SalesOrderMaster select
	public List<SalesOrderMasterDto> SalesOrderMasterSelectDao(SearchDto searchDto);
	
	// SalesOrderList select
	public List<SalesOrderListDto> SalesOrderListSelectDao(SearchDto searchDto);
}
