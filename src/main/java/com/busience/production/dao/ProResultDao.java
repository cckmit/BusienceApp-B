package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.dto.WorkOrderDto;

@Mapper
public interface ProResultDao {
	
	///proResultSelectDao
	public List<ProductionMgmtDto> proResultSelectDao(SearchDto searchDto);
	
	
	//workOrderDetailDao
	public WorkOrderDto workOrderDetailDao(WorkOrderDto workOrderDto);
	
	//proResultUpdateDao
	public int proResultUpdateDao(ProductionMgmtDto productionMgmtDto);
	
}
