package com.busience.system.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.system.dao.CmnDao;
import com.busience.system.dto.CmnDto;
import com.busience.system.service.CmnService;

@Service
public class CmnServiceImpl implements CmnService{

	@Autowired
	CmnDao cmnDao;

	//메뉴 리스트
	@Override
	public List<CmnDto> cmnList() {
        return cmnDao.cmnListDao();
	}	
}
