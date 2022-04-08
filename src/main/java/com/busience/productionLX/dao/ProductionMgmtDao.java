package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;

@Mapper
public interface ProductionMgmtDao {
	
	//생산 실적 관리(제품별)
	public List<ProductionMgmtDto> proItemSumDao(SearchDto searchDto);
	
	//생산 실적 관리(설비별)
	public List<ProductionMgmtDto> proMachineSumDao(SearchDto searchDto);
	
	//proItemListDao
	public List<ProductionMgmtDto> proItemListDao(SearchDto searchDto);
	
	//proMachineListDao
	public List<ProductionMgmtDto> proMachineListDao(SearchDto searchDto);

}
