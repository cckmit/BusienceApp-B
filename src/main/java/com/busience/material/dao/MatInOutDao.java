package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotTransDto;

@Mapper
public interface MatInOutDao {
	
	//랏번호 가져오기
	public List<LotTransDto> MatInOutSelectDao(SearchDto searchDto);
	
}
