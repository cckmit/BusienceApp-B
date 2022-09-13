package com.busience.material.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LotNoDao {
	
	//자재 랏 생성
	public String rawlotNoSelectDao(@Param("inputDate") String inputDate, @Param("itemCode") String itemCode);
	
	//생산LotNo 생성
	public String crateLotNoSelectDao(String itemCode);
	
	//생산LotNo 생성
	public String crateLotNoSelect2Dao(@Param("inputDate") String inputDate, @Param("itemCode") String itemCode);
	
	//생산LotNo 생성
	public String ProdLotNoSelectDao(@Param("inputDate") String inputDate, @Param("itemCode") String itemCode);
		
	//소포장LotNo 생성
	public String smallLotNoSelectDao(String itemCode);
	
	//대포장LotNo 생성
	public String largeLotNoSelectDao(String itemCode);
	
	//Lot번호 업데이트
	public int lotNoMatUpdateDao();
	
}
