package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.SmallPackagingDao;
import com.busience.production.dto.Small_Packaging_tbl;

@Service
public class SmallPackagingService {
	
	@Autowired
	SmallPackagingDao smallPackagingDao;

	public List<Small_Packaging_tbl> smallPackagingListSelect(SearchDto searchDto) {
		return smallPackagingDao.smallPackagingListSelect(searchDto);
	}
	
	// 소포장 list 조회
	public List<Small_Packaging_tbl> smallPackagingSelectDao(SearchDto searchDto) {
		return smallPackagingDao.smallPackagingSelectDao(searchDto);
	}
	
	// 소포장 상자 list 조회
	public List<Small_Packaging_tbl> smallPackagingOneSelectDao(SearchDto searchDto) {
		return smallPackagingDao.smallPackagingOneSelectDao(searchDto);
	}
	
	public List<Small_Packaging_tbl> smallPackagingStandbySelect(SearchDto searchDto) {
		return smallPackagingDao.smallPackagingStandbySelectDao(searchDto);
	}
	
	//당일 소포장 발행 횟수
	public int smallPackagingQtySelect(SearchDto searchDto) {
		return smallPackagingDao.smallPackagingQtySelectDao(searchDto);
	}
		
}
