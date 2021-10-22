package com.busience.common.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.ProductionDao;
import com.busience.common.dto.ProductionDto;
import com.busience.common.service.ProductionService;
import com.busience.productionLX.dto.WorkOrder_tbl;

@Service
public class ProductionServiceImpl implements ProductionService{

	@Autowired
	ProductionDao productionDao;
	
	//가입시 메뉴 저장
	@Override
	public int insertMenuNewUser(ProductionDto productionDto) {
		return productionDao.insertProduction(productionDto);
	}

	//work order 조회
	@Override
	public List<WorkOrder_tbl> getWorkOrder(String code) {
    	return productionDao.selectWorkOrder(code);
	}
}
