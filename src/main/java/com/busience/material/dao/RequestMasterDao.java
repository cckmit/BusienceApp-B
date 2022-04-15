package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestMasterDto;

@Mapper
public interface RequestMasterDao {
	
	//요청master
	public List<RequestMasterDto> requestMasterSelectDao(SearchDto searchDto);
	
	//요청번호
	public String requestNoSelectDao(String RequestNo);
	
	//요청master 저장
	public int requestMasterInsertDao(RequestMasterDto requestMasterDto);

	//요청Master 삭제
	public int requestMasterDeleteDao(String requestNo);
	
	//요청master 업데이트
	public int requestMasterUpdateDao(OutMatDto outMatDto);
	
}
