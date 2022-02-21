package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dto.EquipTemperatureHistoryDto;
import com.busience.productionLX.dao.TempDailyDao;

@Service
public class TempDailyService {

	@Autowired
	TempDailyDao tempDailyDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//작업지시 조회
	public List<EquipTemperatureHistoryDto> TempDailyList(SearchDto searchDto) {
		System.out.println(searchDto);
		return tempDailyDao.TempDailyListDao(searchDto);
	}

}
