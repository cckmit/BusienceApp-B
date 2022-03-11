package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.MatOrderDao;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockMatDto;

@Service
public class MatOrderService {

	@Autowired
	MatOrderDao matOrderDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//matOrderMaster조회	
	public List<OrderMasterDto> matOrderMasterSelect(SearchDto searchDto) {
		return matOrderDao.matOrderMasterSelectDao(searchDto);
	}
	
	//matOrderMaster조회	
	public List<OrderListDto> matOrderListSelect(SearchDto searchDto) {
		return matOrderDao.matOrderListSelectDao(searchDto);
	}
	
	//matOrderMaster조회	
	public List<StockMatDto> stockMatSelect(SearchDto searchDto) {
		return matOrderDao.stockMatSelectDao(searchDto);
	}
	
	//matOrderMaster조회	
	public int matOrderMasterInsert(OrderMasterDto orderMasterDto, String Modifier) {
		return matOrderDao.matOrderMasterInsertDao(orderMasterDto);
	}
}
