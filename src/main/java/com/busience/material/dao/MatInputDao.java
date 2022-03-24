package com.busience.material.dao;

import org.apache.ibatis.annotations.Mapper;

import com.busience.material.dto.InMatDto;

@Mapper
public interface MatInputDao {
	
	//랏번호 가져오기
	public String LotNoSelectDao(InMatDto inMatDto);
	
	//랏마스터 저장
	public int LotMasterInsertDao(InMatDto inMatDto);
	
	//랏트랜스 번호
	public int LotTransNoSelectDao(InMatDto inMatDto);
	
	//랏트랜스 저장
	public int LotTransInsertDao(InMatDto inMatDto);
	
	//자재 입고 등록
	public int inMatInsertDao(InMatDto inMatDto);
	
	//자재 발주list 수정
	public int orderListUpdateDao(InMatDto inMatDto);
	
	//자재 재고 수정
	public int stockMatUpdateDao(InMatDto inMatDto);
	
	//자재 발주master 수정
	public int matOrderMasterUpdateDao(InMatDto inMatDto);
	
	//Lot번호 업데이트
	public int matLotNoUpdateDao();
}
