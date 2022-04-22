package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOutputOrderListDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Mapper
public interface SalesOutputOrderListDao {
	
	// salesOutputOrderList select
	public List<SalesOutputOrderListDto> salesOutputOrderListSelectDao(SearchDto searchDto);
	
	// salesOutputOrderList insert
	public int salesOutputOrderListInsertDao(SalesOutputOrderListDto salesOutputOrderListDto);
	
	// salesOutputOrderList update
	public int salesOutputOrderListUpdateDao(Sales_OutMat_tbl sales_OutMat_tbl);

}
