package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.OrderMasterDto;

@Mapper
public interface OrderMasterDao {
	
	//matOrderMaster조회
	public List<OrderMasterDto> orderMasterSelectDao(SearchDto searchDto);
	
	//발주번호 생성
	public String orderNoCreateDao(OrderMasterDto orderMasterDto);
	
	//matOrderMaster 등록
	public int orderMasterInsertUpdateDao(OrderMasterDto orderMasterDto);	
	
	//OrderMaster 수정
	public int orderMasterUpdateDao(InMatDto inMatDto);
	
	//matOrderMaster 삭제
	public int orderMasterDeleteDao(OrderMasterDto orderMasterDto);	
	
}
