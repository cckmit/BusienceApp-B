package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dao.BOMDao;
import com.busience.standard.dto.BOMDto;

@Service
public class BOMService {

	@Autowired
	BOMDao bomDao;
		
	//조회
	public List<BOMDto> BOMBOMList(SearchDto searchDto) {
		return bomDao.BOMBOMListDao(searchDto);
	}
	
}
