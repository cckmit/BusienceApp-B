package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.dto.ItemDto;

@Mapper
public interface BOMDao {
	
	//조회
	public List<ItemDto> BOMitemListDao(SearchDto searchDto);
	
	//조회
	public List<BOMDto> BOMBOMListDao(SearchDto searchDto);
	
    //조회
    public List<BOMDto> RawMaterialBOMListDao(SearchDto searchDto);
}
