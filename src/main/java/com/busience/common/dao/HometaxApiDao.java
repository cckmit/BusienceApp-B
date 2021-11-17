package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.HometaxApiDto;
import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Mapper
public interface HometaxApiDao {
	
	//해당월에 거래한 거래처리스트
	public List<Sales_OutMat_tbl> transactionCustomerListDao(SearchDto searchDto);
	
	// 세부 테이블 정보 조회
	public List<Sales_OutMat_tbl> transactionDetailListDao(SearchDto searchDto);
	
	//세금계산서 저장
	public int insertTaxInvoiceDao(HometaxApiDto hometaxApiDto);
	
	//세금계산서 조회
	public List<HometaxApiDto> selectTaxInvoiceDao();
	
	//세금계산서 테이블 초기화
	public int deleteHometaxApiDao();

}
