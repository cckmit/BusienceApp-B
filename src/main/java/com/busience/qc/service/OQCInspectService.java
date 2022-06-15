package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dao.SalesOutputDao;
import com.busience.sales.dto.Sales_OutMat_tbl;

@Service
public class OQCInspectService {

	@Autowired
	SalesOutputDao salesOutputDao;
	
	// 영업 출고검사 할 리스트 조회
	public List<Sales_OutMat_tbl> salesOutMatInspectList(SearchDto searchDto) {
		return salesOutputDao.salesOutMatInspectList(searchDto);
	}
}
