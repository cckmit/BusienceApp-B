package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestSubDto;

@Mapper
public interface RequestSubDao {
	
	//요청List
	public List<RequestSubDto> requestSubSelectDao(SearchDto searchDto);
	
	//요청sub 저장
	public int requestSubInsertDao(@Param("list") List<RequestSubDto> RequestSubDtoList,
									@Param("requestNo") String requestNo);
	
	//요청Sub 삭제
	public int requestSubDeleteDao(@Param("list") List<RequestSubDto> RequestSubDtoList,
									@Param("requestNo") String requestNo);
	
	//요청sub 업데이트
	public int RequestSubUpdateDao(OutMatDto outMatDto);
	
}
