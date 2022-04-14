package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.StockDto;

@Service
public class StockService {
	
	@Autowired
	StockDao stockDao;

	//재고테이블 조회
	public List<StockDto> StockSelect(SearchDto searchDto){
		return stockDao.StockSelectDao(searchDto);
	}
}