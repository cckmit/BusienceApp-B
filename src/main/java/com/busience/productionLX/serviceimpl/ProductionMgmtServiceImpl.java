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
		List<ProductionMgmtDto> resultList = productionMgmtDao.proItemListDao(searchDto);
		
		for(ProductionMgmtDto dto : resultList) {
			if(dto.getPRODUCTION_WorkOrder_No() == null) {
				if(dto.getPRODUCTION_Product_Code() == null) {
					dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				}else {
					dto.setPRODUCTION_WorkOrder_ONo("Sub Total");
				}
				dto.setPRODUCTION_Equipment_Code(null);
				dto.setPRODUCTION_Equipment_Name(null);
			}
		}
		return resultList;
	}

	@Override
	public List<ProductionMgmtDto> proMachineList(SearchDto searchDto) {
		List<ProductionMgmtDto> resultList = productionMgmtDao.proMachineListDao(searchDto);

		for(ProductionMgmtDto dto : resultList) {
			if(dto.getPRODUCTION_WorkOrder_No() == null) {
				if(dto.getPRODUCTION_Equipment_Code() == null) {
					dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				}else {
					dto.setPRODUCTION_WorkOrder_ONo("Sub Total");
				}
				dto.setPRODUCTION_Product_Code(null);
				dto.setPRODUCTION_Product_Name(null);
			}
		}
		return resultList;
	}
}
