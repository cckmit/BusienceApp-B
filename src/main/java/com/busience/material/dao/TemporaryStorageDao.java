package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.InMatInspectDto;

@Mapper
public interface TemporaryStorageDao {
	
	//자재 입고 등록
	public int temporaryStorageInsertDao(InMatDto inMatDto);
	
	//입고 리스트 조회
	public List<InMatDto> temporaryStorageSelectDao(SearchDto searchDto);
	
	// 가입고 테이블 update
	public int temporaryStorageUpdateDao(@Param("TS_OrderNo") String TS_OrderNo, @Param("TS_ItemCode") String TS_ItemCode);
}
