package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.dto.WorkOrderDto;

@Mapper
public interface WorkOrderSeiyonDao {
	
	///workOrderSeiyonGapDao
	public List<ProductionMgmtDto> workOrderSeiyonGapDao(WorkOrderDto workOrderDto);

	//workOrderNewUpdateDao
	public int workOrderNewUpdateDao(WorkOrderDto workOrderDto);	
}
