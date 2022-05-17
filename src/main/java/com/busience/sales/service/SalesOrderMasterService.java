package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dao.SalesOrderMasterDao;
import com.busience.sales.dto.SalesOrderMasterDto;

@Service
public class SalesOrderMasterService {
	
	@Autowired
	SalesOrderMasterDao salesOrderMasterDao;

	// 수주 현황
	public List<SalesOrderMasterDto> workOrderSalesOrderSelectDao(SearchDto searchDto) {
		return salesOrderMasterDao.workOrderSalesOrderSelectDao(searchDto);
	}
}
