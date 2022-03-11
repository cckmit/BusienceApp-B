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
	
	//matOrderMaster 등록
	public int matOrderMasterInsertDao(OrderMasterDto orderMasterDto);	
}
