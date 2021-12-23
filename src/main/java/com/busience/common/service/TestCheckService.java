package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.TestCheckDao;
import com.busience.common.dto.TestCheckDto;

@Service
public class TestCheckService {
	
	@Autowired
	TestCheckDao testCheckDao;
		
	public List<TestCheckDto> TestCheckList() {
        return testCheckDao.TestCheckListDao();
	}	
}
