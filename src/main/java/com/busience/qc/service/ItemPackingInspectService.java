package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;

@Service
public class ItemPackingInspectService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto) {
		return lotMasterDao.salesItemListDao(searchDto);
	}
}
