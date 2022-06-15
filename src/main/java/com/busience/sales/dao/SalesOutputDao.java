package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.Sales_OutMat_tbl;

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

	// 반품할 목록 조회
	public List<Sales_OutMat_tbl> salesOutMatReturnSelectDao(SearchDto searchDto);
	
	// 반품 조회
	public List<Sales_OutMat_tbl> salesOutMatReturnListDao(SearchDto searchDto);
	
	// 납품 현황 조회(거래처 출고)
	public List<Sales_OutMat_tbl> salesDeliveryCustomerViewDao(SearchDto searchDto);
	
	// 납품 현황 조회(거래처 리스트)
	public List<Sales_OutMat_tbl> salesDeliveryList(SearchDto searchDto);
	
	// 납품 현황 (거래처별명세서)
	public List<Sales_OutMat_tbl> salesDeliveryCustomerDao(SearchDto searchDto);
	
	// 영업 출고검사 할 리스트 조회
	public List<Sales_OutMat_tbl> salesOutMatInspectList(SearchDto searchDto);
}
