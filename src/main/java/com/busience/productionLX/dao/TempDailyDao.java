package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.EquipTemperatureHistoryDto;

@Mapper
public interface TempDailyDao {
	
	///workOrderSelectDao
	public List<EquipTemperatureHistoryDto> TempDailyListDao(SearchDto searchDto);
	
}
