package com.busience.qc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.IqcDto;

@Mapper
public interface IqcRateDao {
	
	//완료된 작업지시 내역
	public List<IqcDto> iqcRateSelectDao(SearchDto searchDto);
}
