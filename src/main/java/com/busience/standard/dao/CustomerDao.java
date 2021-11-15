package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.CustomerDto;

@Mapper
public interface CustomerDao {

	//조회
	public List<CustomerDto> selectCustomerListDao();
	
	//등록
	public int insertCustomerDao(CustomerDto CustomerDto);
	
	//수정
	public int updateCustomerDao(CustomerDto CustomerDto);
	
	//삭제
	public int deleteCustomerDao(String CustomerCode);
}
