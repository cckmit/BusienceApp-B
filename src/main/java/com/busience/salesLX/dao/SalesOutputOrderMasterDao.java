package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;

@Mapper
public interface SalesOutputOrderMasterDao {

	// salesOutputOrderMaster select
	public List<SalesOutputOrderMasterDto> salesOutputOrderMasterSelectDao(SearchDto searchDto);

	// salesOutputOrderNo create
	public String salesOutputOrderNoCreateDao(SalesOutputOrderMasterDto salesOutputOrderMasterDto);
	
	// salesOutputOrderMaster insert
	public int salesOutputOrderMasterInsertDao(SalesOutputOrderMasterDto salesOutputOrderMasterDto);
	
	// salesOutputOrderMaster report
	public List<SalesOutputOrderMasterDto> salesOutputOrderListDao(SearchDto searchDto);
	
	// salesOutputOrderMaster update
	public int salesOutputOrderMasterUpdateDao(String Sales_Output_Order_mOrder_No);
}
