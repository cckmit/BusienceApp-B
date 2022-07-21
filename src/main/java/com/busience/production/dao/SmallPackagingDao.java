package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.Small_Packaging_tbl;

@Mapper
public interface SmallPackagingDao {

	// 소포장 현황
	public List<Small_Packaging_tbl> smallPackagingListSelect(SearchDto searchDto);
	
	// 소포장 list 조회
	public List<Small_Packaging_tbl> smallPackagingSelectDao(SearchDto searchDto);
	
	// 소포장 상자 list 조회
	public List<Small_Packaging_tbl> smallPackagingOneSelectDao(SearchDto searchDto);
	
	public List<Small_Packaging_tbl> smallPackagingStandbySelectDao(SearchDto searchDto);
	
	public int smallPackagingQtySelectDao(SearchDto searchDto);
	//소포장 등록
	public int smallPackagingInsertDao(Small_Packaging_tbl small_Packaging_tbl);
}
