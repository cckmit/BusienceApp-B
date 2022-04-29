package com.busience.salesLX.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.salesLX.dto.Sales_InMat_tbl;

@Mapper
public interface SalesInputDao {

	//영업 랏번호 가져오기
	public String salesLotNoSelectDao(Sales_InMat_tbl sales_InMat_tbl);
	
	//영업 Lot번호 업데이트
	public int salesLotNoSalesUpdateDao();
	
	//sales_InMat_tbl insert
	public int salesInMatInsertDao(Sales_InMat_tbl sales_InMat_tbl);
	
}
