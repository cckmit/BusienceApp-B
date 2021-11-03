package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.ProductionDto;
import com.busience.productionLX.dto.WorkOrder_tbl;

@Mapper
public interface ProductionDao {

	//작업시작 (S)인 작업지시 select
	public List<WorkOrder_tbl> selectWorkOrder(@Param("equip_id") String string);
	
	//제품 생산 리스트 등록
	public int insertProduction(ProductionDto productionDto);
}
