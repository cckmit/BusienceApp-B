package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;

@Service
public class SalesOutputService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	// 영업 LotMaster 조회
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto) {
		return lotMasterDao.salesOutputLotMasterDao(searchDto);
	}
}
