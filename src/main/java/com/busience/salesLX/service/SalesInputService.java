package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;

@Service
public class SalesInputService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	// LotMaster select
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto) {
		return lotMasterDao.salesInputLotMasterSelectDao(lotMasterDto);
	}
	
}
