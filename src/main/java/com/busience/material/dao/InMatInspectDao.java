package com.busience.material.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.material.dto.InMatInspectDto;

@Mapper
public interface InMatInspectDao {

	public int InMatInspectInsertDao(InMatInspectDto inMatInspectDto);
}
