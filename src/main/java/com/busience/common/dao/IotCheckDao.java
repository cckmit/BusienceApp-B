package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.IotCheckDto;

@Mapper
public interface IotCheckDao {
	
	public List<IotCheckDto> IotCheckListDao();
	
	public int IotInsertDao(IotCheckDto iotCheckDto);
	
}
