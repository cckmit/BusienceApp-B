package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.SalesOrderMasterDto;

@Mapper
public interface SalesOrderMasterDao {

	// SalesOrderMaster select
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto);
	
	// SalesOrderNo create
	public String salesOrderNoCreateDao(SalesOrderMasterDto salesOrderMasterDto);
	
	// 수주 현황
	public List<SalesOrderMasterDto> workOrderSalesOrderSelectDao(SearchDto searchDto);
	
	// SalesOrderMaster insert
	public int salesOrderMasterInsertUpdateDao(SalesOrderMasterDto salesOrderMasterDto);
	
	// SalesOrderMaster delete
	public int salesOrderMasterDeleteDao(String orderNo);
	
	// salesOrderMaster update
	public int salesOrderMasterUpdateDao(String Sales_Order_mCus_No);
}
