package com.busience.productionLX.service;

import java.util.List;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;

public interface ProductionMgmtService {
	
	//품목별
    public List<ProductionMgmtDto> proItemList(SearchDto searchDto);
    
    //기계별
    public List<ProductionMgmtDto> proMachineList(SearchDto searchDto);   

}
