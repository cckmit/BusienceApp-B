package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.SalesOrderListDto;
import com.busience.sales.dto.SalesOrderMasterDto;

@Mapper
public interface SalesOrderDao {

	// SalesOrderMaster select
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto);
	
	// SalesOrderNo create
	public String salesOrderNoCreateDao(SalesOrderMasterDto salesOrderMasterDto);
	
	// SalesOrderList select
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto);

	// SalesOrderMaster insert
	public int salesOrderMasterInsertUpdateDao(SalesOrderMasterDto salesOrderMasterDto);
	
	// SalesOrderList insert
	public int salesOrderListInsertUpdateDao(SalesOrderListDto salesOrderListDto);

	// SalesOrderMaster delete
	public int salesOrderMasterDeleteDao(String orderNo);
	
	// SalesOrderList delete
	public int salesOrderListDeleteDao(@Param("orderNo") String orderNo, @Param("list") List<SalesOrderListDto> salesOrderListDto);
	
}
