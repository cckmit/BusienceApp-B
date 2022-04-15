package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.LotTransDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface LotTransDao {
	
	//랏트랜스 번호
	public int lotTransNoSelectDao(InMatDto inMatDto);
	
	//랏트랜스 저장
	public int lotTransInsertDao(InMatDto inMatDto);
	
	//입출고 조회
	public List<LotTransDto> inOutMatSelectDao(SearchDto searchDto);
	
	//랏트랜스 번호
	public int lotTransNoSelectDao2(OutMatDto outMatDto);
	
	//랏트랜스 저장
	public int lotTransInsertDao2(OutMatDto outMatDto);
}
