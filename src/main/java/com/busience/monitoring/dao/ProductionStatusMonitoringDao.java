package com.busience.monitoring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.ChangedProductionStatusMonitoringDto;
import com.busience.monitoring.dto.ChangedWrappingStatusMonitoringDto;
import com.busience.monitoring.dto.ProductionStatusMonitoringDto;
import com.busience.monitoring.dto.WrappingStatusMonitoringDto;

@Mapper
public interface ProductionStatusMonitoringDao {	
	//생산 모니터링테이블 가져오기
	public List<ProductionStatusMonitoringDto> getProductionMonitoringDao(SearchDto searchDto);
	
	//포장 모니터링테이블 가져오기
	public List<WrappingStatusMonitoringDto> getWrappingMonitoringDao(SearchDto searchDto);		
	
	//바뀐 생산 모니터링테이블 가져오기
	public List<ChangedProductionStatusMonitoringDto> getChangedProductionMonitoringDao(SearchDto searchDto);
	
	//바뀐 포장 모니터링테이블 가져오기
	public List<ChangedWrappingStatusMonitoringDto> getChangedWrappingMonitoringDao(SearchDto searchDto);		
}
