package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;

@Mapper
public interface ProductionMgmtDao {
	
	//proItemListDao
	public List<ProductionMgmtDto> proItemListDao(SearchDto searchDto);

}
