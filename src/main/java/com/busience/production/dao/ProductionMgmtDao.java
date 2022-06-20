package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProductionMgmtDto;

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
	
	//마스크 실적 현황
	public List<ProductionMgmtDto> proMaskSumDao(SearchDto searchDto);
	
	//생간 포장 현황
	public List<ProductionMgmtDto> proPackingDao(SearchDto searchDto);
	
	//자재 투입 현황
	
	//Lot 발행 조회
	
	//Lot 이력 조회

}
