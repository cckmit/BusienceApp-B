package com.busience.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.busience.common.dto.ProductionDto;
import com.busience.productionLX.dto.WorkOrder_tbl;

public interface ProductionService {

	//가입시 메뉴 저장
    public int insertMenuNewUser(ProductionDto productionDto);
    
    //work order 조회
    public List<WorkOrder_tbl> getWorkOrder(String code);
}
