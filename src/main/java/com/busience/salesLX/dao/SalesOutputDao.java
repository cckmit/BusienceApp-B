package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Mapper
public interface SalesOutputDao {

	// salesOutMatNo Create
	public String salesOutMatNoCreateDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
	// salesOutMat select 
	public List<Sales_OutMat_tbl> salesOutMatSelectDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
	// salesOutMat insert
	public int salesOutMatInsertDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
}
