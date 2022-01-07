package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Mapper
public interface WorkOrderDao {
	
	///workOrderSelectDao
	public List<WorkOrderDto> workOrderSelectDao(SearchDto searchDto);
	
	public int workOrderUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderDeleteDao(WorkOrderDto workOrderDto);
	
	public int workOrderCountDao(WorkOrderDto workOrderDto);
}
