package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.EquipWorkOrderDto;

@Mapper
public interface EquipWorkOrderDao {
	
	public List<EquipWorkOrderDto> equipWorkOrderSelectDao(SearchDto searchDto);
	
	// itemCode 검색
	public String equipWorkItemCodeSelectDao(EquipWorkOrderDto equipWorkOrderDto);
	
	// insert
	public int equipWorkOrderInsert(EquipWorkOrderDto equipWorkOrderDto);
	
	// update
	public int equipWorkOrderUpdate(EquipWorkOrderDto equipWorkOrderDto);
	
}
