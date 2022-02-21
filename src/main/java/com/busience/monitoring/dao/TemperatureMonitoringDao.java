package com.busience.monitoring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.EquipMonitoringDto;
import com.busience.monitoring.dto.EquipTemperatureHistoryDto;
import com.busience.monitoring.dto.TempChartDto;

@Mapper
public interface TemperatureMonitoringDao {
	
	//모니터링테이블 검색
	public List<EquipMonitoringDto> selectEquipMonitoringDao(SearchDto searchDto);
	
	//이력테이블 검색
	public List<EquipTemperatureHistoryDto> selectEquipTemperatureHistoryDao(SearchDto searchDto);
	//온도 저장
	public int insertTemperatureDao(EquipMonitoringDto equipMonitoringDto);
	
	//온도 저장
	public int updateTemperatureDao(EquipMonitoringDto equipMonitoringDto);
	
	//시작 온도 데이터
	public List<TempChartDto> startTemperatureDataDao(SearchDto searchDto);
	
	//끝 온도 데이터
	public List<TempChartDto> endTemperatureDataDao(SearchDto searchDto);
	
	//위 온도 데이터
	public List<TempChartDto> upperTemperatureDataDao(SearchDto searchDto);
	
	//밑 온도 데이터
	public List<TempChartDto> underTemperatureDataDao(SearchDto searchDto);
	
	//온도 데이터 갯수
	public int countTemperatureDataDao(SearchDto searchDto);
	
	//하루치 작업지시갯수
	public int tempDailyOrderCountDao(EquipMonitoringDto equipMonitoringDto);
}
