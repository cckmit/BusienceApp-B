package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dao.SalesPackingDao;
import com.busience.sales.dto.SalesPackingDto;

@Service
public class SalesPackingService {

	@Autowired
	SalesPackingDao salesPackingDao;
	
	// sales_packing_select
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesPackingListSelectDao(searchDto);
	}
	
	// 입고 반품 조회
	public List<SalesPackingDto> salesInMatReturnSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesInMatReturnSelectDao(searchDto);
	}
	
	// 입고 반품 리스트
	public List<SalesPackingDto> salesInMatReturnListDao(SearchDto searchDto) {
		return salesPackingDao.salesInMatReturnListDao(searchDto);
	}
	
	// 포장 관리 조회
	public List<SalesPackingDto> salesPackingSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesPackingSelectDao(searchDto);
	}
}
