package com.busience.productionLX.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.ProductionMgmtDao;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.service.ProductionMgmtService;



@Service
public class ProductionMgmtServiceImpl implements ProductionMgmtService{
	
	@Autowired
	ProductionMgmtDao productionMgmtDao;
	
	// 코드 조건으로 조회
	@Override
	public List<ProductionMgmtDto> proItemList(SearchDto searchDto) {
        return productionMgmtDao.proItemListDao(searchDto);
	}
}
