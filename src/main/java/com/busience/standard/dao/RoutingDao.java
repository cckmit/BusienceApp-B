package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.RoutingDto;

@Mapper
public interface RoutingDao {
	
	//조회
	public List<RoutingDto> selectRoutingListDao();
	
	//조회
	public List<RoutingDto> selectRoutingDetailDao(RoutingDto routingDto);
	
	//등록
	public int insertRoutingDao(RoutingDto routingDto);
	
	//수정
	public int updateRoutingDao(RoutingDto routingDto);
}
