package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface LotMasterDao {
	
	//랏마스터 insert update
	public int lotMasterInsertUpdateDao(InMatDto inMatDto);
	
	//랏마스터조회
	public List<LotMasterDto> lotMasterSelectDao(SearchDto searchDto);
	
	//랏마스터 insert
	public int lotMasterInsertDao(OutMatDto outMatDto);
	
	//랏마스터 update
	public int lotMasterUpdateDao(OutMatDto outMatDto);
	
}
