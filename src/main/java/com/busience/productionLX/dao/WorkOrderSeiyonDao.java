package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Mapper
public interface WorkOrderSeiyonDao {
	
	///workOrderSeiyonGapDao
	public List<ProductionMgmtDto> workOrderSeiyonGapDao(WorkOrderDto workOrderDto);

	//workOrderNewUpdateDao
	public int workOrderNewUpdateDao(WorkOrderDto workOrderDto);	
}
