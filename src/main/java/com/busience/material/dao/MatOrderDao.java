package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockMatDto;

@Mapper
public interface MatOrderDao {
	
	//matOrderMaster조회
	public List<OrderMasterDto> matOrderMasterSelectDao(SearchDto searchDto);
	
	//matOrderList조회
	public List<OrderListDto> matOrderListSelectDao(SearchDto searchDto);
	
	//matOrderList조회
	public List<StockMatDto> stockMatSelectDao(SearchDto searchDto);
	
	//발주번호 생성
	public String matOrderNoCreateDao(OrderMasterDto orderMasterDto);
	
	//matOrderMaster 등록
	public int matOrderMasterInsertUpdateDao(OrderMasterDto orderMasterDto);	
	
	//matOrderList 등록
	public int matOrderListInsertUpdateDao(OrderListDto orderListDto);
	
	//matOrderMaster 삭제
	public int matOrderMasterDeleteDao(OrderMasterDto orderMasterDto);	
	
	//matOrderList 등록
	public int matOrderListDeleteDao(OrderListDto orderListDto);
}
