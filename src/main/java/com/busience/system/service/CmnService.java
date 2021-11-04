package com.busience.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.system.dao.CmnDao;
import com.busience.system.dto.CmnDto;

@Service
public class CmnService {

	@Autowired
	CmnDao cmnDao;

	//메뉴 리스트
	public List<CmnDto> cmnList() {
        return cmnDao.cmnListDao();
	}
}
