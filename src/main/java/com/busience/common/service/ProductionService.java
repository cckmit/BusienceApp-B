package com.busience.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.busience.common.domain.Production;
import com.busience.common.mapper.ProductionMapper;
import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.standard.Dto.DTL_TBL;

@Service
public class ProductionService {

	private ProductionMapper productionMapper;
			
	public ProductionService(ProductionMapper productionMapper) {
		this.productionMapper = productionMapper;
	}
		
	//가입시 메뉴 저장
    public int insertMenuNewUser(Production production) {
    	return productionMapper.insertProduction(production);
    }
    
    //work order 조회
    public List<WorkOrder_tbl> getWorkOrder(String code) {
    	final List<WorkOrder_tbl> WorkOrder = productionMapper.selectWorkOrder(code);
    	return WorkOrder;
    }
}
