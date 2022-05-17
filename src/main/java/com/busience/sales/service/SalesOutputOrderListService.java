package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dao.SalesOutputOrderListDao;
import com.busience.sales.dto.SalesOutputOrderListDto;

@Service
public class SalesOutputOrderListService {
	
	@Autowired
	SalesOutputOrderListDao salesOutputOrderListDao;

	// salesOutputOrderList select
	public List<SalesOutputOrderListDto> salesOutputOrderListSelectDao(SearchDto searchDto) {
		return salesOutputOrderListDao.salesOutputOrderListSelectDao(searchDto);
	}
}
