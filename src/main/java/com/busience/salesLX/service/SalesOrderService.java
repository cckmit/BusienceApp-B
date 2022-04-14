package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dao.SalesOrderDao;
import com.busience.salesLX.dto.SalesOrderListDto;
import com.busience.salesLX.dto.SalesOrderMasterDto;

@Service
public class SalesOrderService {

	@Autowired
	SalesOrderDao salesOrderDao;
	
	// SalesOrderMaster select
	public List<SalesOrderMasterDto> SalesOrderMasterSelectDao(SearchDto searchDto) {
		return salesOrderDao.SalesOrderMasterSelectDao(searchDto); 
	}
	
	// SalesOrderList select
	public List<SalesOrderListDto> SalesOrderListSelectDao(SearchDto searchDto) {
		return salesOrderDao.SalesOrderListSelectDao(searchDto);
	}
}
