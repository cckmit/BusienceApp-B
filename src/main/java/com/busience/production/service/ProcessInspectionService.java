package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.ProcessInspectDao;
import com.busience.production.dto.ProcessInspectDto;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class ProcessInspectionService {
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	ProcessInspectDao processInspectDao;
	
	// 검사할 상자 list
	public List<CrateLotDto> crateInspectSelectDao(SearchDto searchDto) {
		return crateLotDao.crateInspectSelectDao(searchDto);
	}
	
	// 검사된 list
	public List<ProcessInspectDto> processInspectListDao(SearchDto searchDto) {
		return processInspectDao.processInspectListDao(searchDto);
	}
	
	// 검사 form 조회
	public List<ProcessInspectDto> processInspectOneSelectDao(SearchDto searchDto) {
		return processInspectDao.processInspectOneSelectDao(searchDto);
	}
	
	public int processInspectInsert(ProcessInspectDto processInspectDto) {
		return processInspectDao.processInspectInsertDao(processInspectDto);
	}
}
