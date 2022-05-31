 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class MaskInputService {
	
	@Autowired
	CrateLotDao crateLotDao;

	public List<CrateLotDto> crateLotListSelect(SearchDto searchDto) {
		return crateLotDao.crateLotListSelectDao(searchDto);
	}
	
	public int crateLotUpdate2(CrateLotDto crateLotDto) {
		return crateLotDao.crateLotUpdateDao2(crateLotDto);
	}
}
