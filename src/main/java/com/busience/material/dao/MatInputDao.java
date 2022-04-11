package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
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
	public int InMatInsertDao(InMatDto inMatDto);
	
	//자재 발주list 수정
	public int OrderListUpdateDao(InMatDto inMatDto);
	
	//자재 재고 수정
	public int StockMatUpdateDao(InMatDto inMatDto);
	
	//자재 발주master 수정
	public int MatOrderMasterUpdateDao(InMatDto inMatDto);
	
	//Lot번호 업데이트
	public int MatLotNoUpdateDao();
	
	//입고 리스트 조회
	public List<InMatDto> matInputListDao(SearchDto searchDto);
	
	//입고 리스트 조건별 조회
	public List<InMatDto> matInputOtherListDao(SearchDto searchDto);
	
	//납품 명세서 거래처 리스트
	public List<InMatDto> matInputDeliveryMasterDao(SearchDto searchDto);
	
	//납품 명세서 세부 리스트
	public List<InMatDto> matInputDeliverySubDao(SearchDto searchDto);
}
