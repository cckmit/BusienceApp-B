package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.ItemDto;

@Mapper
public interface SalesLabelPrintDao {
	
	//조회
	public List<ItemDto> salesLabelPrintListDao();
}
