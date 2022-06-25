 package com.busience.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class ProductionStatusService {

	@Autowired
	CrateLotDao crateLotDao;
	
	
	public CrateLotDto productionStatusSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateLotDao.crateLotSelectDao(searchDto);
	}
}
