package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.RequestMasterDto;
import com.busience.material.dto.RequestSubDto;

@Mapper
public interface MatRequestDao {
	
	//요청master
	public List<RequestMasterDto> RequestMasterSelectDao(SearchDto searchDto);
	
	//요청List
	public List<RequestSubDto> RequestSubSelectDao(SearchDto searchDto);
	
	//요청번호
	public String RequestNoSelectDao(String RequestNo);
	
	//요청master 저장
	public int RequestMasterInsertDao(RequestMasterDto requestMasterDto);
	
	//요청sub 저장
	public int RequestSubInsertDao(@Param("list") List<RequestSubDto> RequestSubDtoList,
									@Param("requestNo") String requestNo);
	
	//요청Master 삭제
	public int RequestMasterDeleteDao(String requestNo);
	
	//요청Sub 삭제
	public int RequestSubDeleteDao(@Param("list") List<RequestSubDto> RequestSubDtoList,
									@Param("requestNo") String requestNo);
	
}
