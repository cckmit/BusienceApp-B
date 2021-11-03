package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.ProductionDao;
import com.busience.common.dto.ProductionDto;
import com.busience.productionLX.dto.WorkOrder_tbl;

@Service
public class ProductionService {

	@Autowired
	ProductionDao productionDao;
	
	//가입시 메뉴 저장
	public int insertMenuNewUser(ProductionDto productionDto) {
		return productionDao.insertProduction(productionDto);
	}

	//work order 조회
	public List<WorkOrder_tbl> getWorkOrder(String code) {
    	return productionDao.selectWorkOrder(code);
	}
}
