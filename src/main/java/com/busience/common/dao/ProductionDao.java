package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.ProductionDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Mapper
public interface ProductionDao {

	//작업시작 (S)인 작업지시 select
	public List<WorkOrderDto> selectWorkOrderDao(String equip);
	
	//제품 생산 리스트 등록
	public int insertProductionDao(ProductionDto productionDto);
	
	//작업지시 업데이트
	public int updateWorkOrderDao(ProductionDto productionDt);
}
