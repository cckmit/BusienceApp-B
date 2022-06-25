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
}
