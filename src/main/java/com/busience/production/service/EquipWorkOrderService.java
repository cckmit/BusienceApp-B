package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dto.EquipWorkOrderDto;

@Service
public class EquipWorkOrderService {

	@Autowired
	EquipWorkOrderDao equipWorkOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	public List<EquipWorkOrderDto> equipWorkOrderSelect(SearchDto searchDto) {
		return equipWorkOrderDao.equipWorkOrderSelectDao(searchDto);
	}
}
