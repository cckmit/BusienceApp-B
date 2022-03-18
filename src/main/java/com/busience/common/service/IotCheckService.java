package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.IotCheckDao;
import com.busience.common.dto.IotCheckDto;

@Service
public class IotCheckService {
	
	@Autowired
	IotCheckDao iotCheckDao;
		
	public List<IotCheckDto> IotCheckList() {
        return iotCheckDao.IotCheckListDao();
	}	
}
