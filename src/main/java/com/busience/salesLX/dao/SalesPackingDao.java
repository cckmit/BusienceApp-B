package com.busience.salesLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesPackingDto;

@Mapper
public interface SalesPackingDao {
	
	// sales_packingNo create
	public SalesPackingDto salesPackingNoCreateDao(SalesPackingDto salesPackingDto);
	
	// sales_packing_select
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto);
	
	// sales_packing_tbl insert
	public int salesPackingInsertDao(SalesPackingDto salesPackingDto);

}
