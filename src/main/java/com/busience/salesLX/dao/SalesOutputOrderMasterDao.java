package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;

@Mapper
public interface SalesOutputOrderMasterDao {

	// salesOutputOrderMaster select
	public List<SalesOutputOrderMasterDto> salesOutputOrderMasterSelectDao(SearchDto searchDto);

	// salesOutputOrderNo create
	public String salesOutputOrderNoCreateDao(SalesOutputOrderMasterDto salesOutputOrderMasterDto);
}
