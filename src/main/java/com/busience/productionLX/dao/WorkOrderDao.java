package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Mapper
public interface WorkOrderDao {
	
	///proResultSelectDao
	public List<ProductionMgmtDto> proResultSelectDao(SearchDto searchDto);
	
	
	//workOrderDetailDao
	public WorkOrderDto workOrderDetailDao(WorkOrderDto workOrderDto);
	
	//proResultUpdateDao
	public int proResultUpdateDao(ProductionMgmtDto productionMgmtDto);
	
}
