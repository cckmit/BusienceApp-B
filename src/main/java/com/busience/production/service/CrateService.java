package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dto.CrateDto;

@Service
public class CrateService {

	@Autowired
	CrateDao crateDao;
	
	public List<CrateDto> crateSelectDao(CrateDto crateDto) {
		return crateDao.crateSelectDao(crateDto);
	}
	
	public int crateSaveDao(CrateDto crateDto) {
		return crateDao.crateSaveDao(crateDto);
	}
	
	public int crateUpdateDao(CrateDto crateDto) {
		return crateDao.crateUpdateDao(crateDto);
	}
	
}
