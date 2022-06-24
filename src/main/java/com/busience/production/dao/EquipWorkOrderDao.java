package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;

@Mapper
public interface EquipWorkOrderDao {
	
	public List<WorkOrderDto> packagingLineListSelectDao(SearchDto searchDto);
}
