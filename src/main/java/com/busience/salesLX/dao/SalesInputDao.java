package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

@Mapper
public interface SalesInputDao {

	//영업 랏번호 가져오기
	public String salesLotNoSelectDao(Sales_InMat_tbl sales_InMat_tbl);
	
	//sales_InMat_tbl select
	public List<Sales_InMat_tbl> salesInMatSelectDao();
	
	//영업 Lot번호 업데이트
	public int salesLotNoSalesUpdateDao();
	
	//sales_InMat_tbl insert
	public int salesInMatInsertDao(Sales_InMat_tbl sales_InMat_tbl);
	
	//salesInMat List select
	public List<Sales_InMat_tbl> salesInputListDao(@Param("Sales_InMat_Code") String Sales_InMat_Code, @Param("Sales_InMat_Lot_No") String Sales_InMat_Lot_No, @Param("Sales_InMat_Rcv_Clsfc") String Sales_InMat_Rcv_Clsfc,  
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	//salesInMat Item View
	public List<Sales_InMat_tbl> salesInputItemViewDao(@Param("Sales_InMat_Code") String Sales_InMat_Code, @Param("Sales_InMat_Rcv_Clsfc") String Sales_InMat_Rcv_Clsfc, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	//영업 반품 조회
	public List<Sales_InMat_tbl> salesInMatReturnSelectDao(SearchDto searchDto);
	
	//salesInMat update
	public int salesInMatUpdateDao(Sales_InMat_tbl sales_InMat_tbl);
	
}
