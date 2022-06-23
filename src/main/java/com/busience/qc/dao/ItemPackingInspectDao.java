package com.busience.qc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.ItemPackingInspectDto;

@Mapper
public interface ItemPackingInspectDao {
	
	// LotNo로 조회
	public List<ItemPackingInspectDto> itemPackingInspectListDao(SearchDto searchDto);
	
	// list
	public List<ItemPackingInspectDto> itemPackingInspectSearchDao(SearchDto searchDto);

	public int itemPackingInspectInsertDao(ItemPackingInspectDto itemPackingInspectDto);
}
