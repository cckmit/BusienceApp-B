package com.busience.production.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;

@Service
public class EquipWorkOrderService {

	@Autowired
	DtlDao dtlDao;

	@Autowired
	TransactionTemplate transactionTemplate;

}
