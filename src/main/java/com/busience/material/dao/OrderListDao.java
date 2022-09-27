package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.OrderListDto;

@Mapper
public interface OrderListDao {

	//matOrderList조회
	public List<OrderListDto> orderListSelectDao(SearchDto searchDto);
	
	//matOrderList 등록
	public int orderListInsertUpdateDao(OrderListDto orderListDto);
	
	//자재 발주list 수정
	public int orderListUpdateDao(InMatDto inMatDto);
	
	//matOrderList 등록
	public int orderListDeleteDao(@Param("orderNo") String orderNo, @Param("list") List<OrderListDto> orderListDto);
}
