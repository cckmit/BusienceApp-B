package com.busience.productionLX.service;

import java.util.List;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;

public interface ProductionMgmtService {
	
	// 코드 조건으로 조회
    public List<ProductionMgmtDto> proItemList(SearchDto searchDto);
    

}
