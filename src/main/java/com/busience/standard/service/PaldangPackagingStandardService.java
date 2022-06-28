package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.standard.dao.paldangPackagingStandardDao;
import com.busience.standard.dto.PaldangPackagingStandardDto;

@Service
public class PaldangPackagingStandardService {
	
	@Autowired
	paldangPackagingStandardDao paldangPackagingDao;

	public List<PaldangPackagingStandardDto> paldangPackagingListDao() {
		return paldangPackagingDao.paldangPackagingListDao();
	}
}
