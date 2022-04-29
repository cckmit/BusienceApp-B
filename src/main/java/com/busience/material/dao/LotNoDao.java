package com.busience.material.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.material.dto.InMatDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

@Mapper
public interface LotNoDao {
	
	//랏번호 가져오기
	public String lotNoSelectDao(InMatDto inMatDto);
	
	//Lot번호 업데이트
	public int lotNoMatUpdateDao();
	
}