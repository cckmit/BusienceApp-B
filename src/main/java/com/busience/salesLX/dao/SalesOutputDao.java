package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Mapper
public interface SalesOutputDao {

	// salesOutMatNo Create
	public String salesOutMatNoCreateDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
	// salesOutMat select 
	public List<Sales_OutMat_tbl> salesOutMatSelectDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
	// salesOutMat insert
	public int salesOutMatInsertDao(Sales_OutMat_tbl sales_OutMat_tbl);
	
	// selecOutMat List select
	public List<Sales_OutMat_tbl> salesOutMatSelectListDao(@Param("Sales_OutMat_Code") String Sales_OutMat_Code, @Param("Sales_OutMat_Client_Code") String Sales_OutMat_Client_Code, @Param("Sales_OutMat_Send_Clsfc") String Sales_OutMat_Send_Clsfc, @Param("Sales_OutMat_Lot_No") String Sales_OutMat_Lot_No, 
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	// salesOutMat Item View
	public List<Sales_OutMat_tbl> salesOutMatItemViewDao(@Param("Sales_OutMat_Code") String Sales_OutMat_Code, @Param("Sales_OutMat_Send_Clsfc") String Sales_OutMat_Send_Clsfc, @Param("startDate") String startDate, @Param("endDate") String endDate);

}