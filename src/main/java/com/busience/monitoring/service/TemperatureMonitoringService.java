package com.busience.monitoring.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dao.TemperatureMonitoringDao;
import com.busience.monitoring.dto.EquipMonitoringDto;
import com.busience.monitoring.dto.TempChartDto;

@Service
public class TemperatureMonitoringService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TemperatureMonitoringDao temperatureMonitoringDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//조회	
	public List<EquipMonitoringDto> selectEquipMonitoring(SearchDto searchDto) {
		return temperatureMonitoringDao.selectEquipMonitoringDao(searchDto);
	}
	
	//차트 데이터를 추출
	public List<TempChartDto> tempChartDatas(SearchDto searchDto) {
		List<TempChartDto> tempChartDtoList = new ArrayList<TempChartDto>();
		TempChartDto startData = temperatureMonitoringDao.startTemperatureDataDao(searchDto).get(0);
		TempChartDto endData = temperatureMonitoringDao.endTemperatureDataDao(searchDto).get(0);

		int count = temperatureMonitoringDao.countTemperatureDataDao(searchDto);					
				
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime startDateTime = LocalDateTime.parse(startData.getChartTime(), DTF);
		LocalDateTime endDateTime = LocalDateTime.parse(endData.getChartTime(), DTF);
		
		//행갯수가 7개면 사잇값은 5개, 나눠야되는값은 6
		if(count > 7) {
			count = 7;
		}
		int repeat = count-1;
		tempChartDtoList.add(startData);
		
		for(int i=1;i<repeat;i++) {
			long gapTime = ChronoUnit.SECONDS.between(startDateTime, endDateTime)/(repeat);
			
			LocalDateTime targetTime = startDateTime.plusSeconds(gapTime*i);
			searchDto.setTargetTime(targetTime.format(DTF));
			
			TempChartDto upperData = temperatureMonitoringDao.upperTemperatureDataDao(searchDto).get(0);
			TempChartDto underData = temperatureMonitoringDao.underTemperatureDataDao(searchDto).get(0);

			LocalDateTime upperTime = LocalDateTime.parse(upperData.getChartTime(), DTF);
			LocalDateTime underTime = LocalDateTime.parse(underData.getChartTime(), DTF);

			// (targetTime-underTime)/(upperTime-underTime)*사이온도 + 언더 온도값
			float targetTemp = (ChronoUnit.SECONDS.between(targetTime, upperTime)*underData.getChartTemp()
					+ ChronoUnit.SECONDS.between(underTime, targetTime)*upperData.getChartTemp())
					/ChronoUnit.SECONDS.between(underTime, upperTime);
			
			TempChartDto targetData = new TempChartDto();
			targetData.setChartTemp(targetTemp);
			targetData.setChartTime(targetTime.format(DTF));
			tempChartDtoList.add(targetData);
		}
		if(count > 1) {
			tempChartDtoList.add(endData);			
		}
		return tempChartDtoList;
	}
	
	public int updateTemperature(EquipMonitoringDto equipMonitoringDto){		
		//설비명
		String MachineCode = equipMonitoringDto.getEquip_Code();		
		//시간
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		equipMonitoringDto.setEquip_Time(today);		
		//갯수
		int number = temperatureMonitoringDao.tempDailyOrderCountDao(equipMonitoringDto);
		
		//작업지시번호
		String Equip_No = today +"-"+ MachineCode +"-"+ String.format("%02d", number+1);
		
		equipMonitoringDto.setEquip_Code(MachineCode);
		equipMonitoringDto.setEquip_No(Equip_No);

		System.out.println("작업지시확인");
		System.out.println(equipMonitoringDto);
		return  temperatureMonitoringDao.updateTemperatureDao(equipMonitoringDto);
	}
}
