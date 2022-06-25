package com.busience.material.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.material.dto.InMatDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface LotNoDao {
	
	//랏번호 가져오기
	public String lotNoSelectDao(InMatDto inMatDto);
	
	//재고 조정에서 필요한 랏번호 조회
	public String outMatlotNoSelectDao(OutMatDto outMatDto);
	
	//생산LotNo 생성
	public String crateLotNoSelectDao(String itemCode);
	
	//소포장LotNo 생성
	public String smallLotNoSelectDao(String itemCode);
	
	//대포장LotNo 생성
	public String largeLotNoSelectDao(String itemCode);
	
	//Lot번호 업데이트
	public int lotNoMatUpdateDao();
	
}
