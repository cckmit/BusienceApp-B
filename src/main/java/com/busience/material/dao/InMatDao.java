package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;

@Mapper
public interface InMatDao {
	
	//lot 순번 가져오기
	public int inMatNoSelectDao(String InMat_Lot_No);
	
	//자재 입고 등록
	public int inMatInsertDao(InMatDto inMatDto);
	
	//입고 리스트 조회
	public List<InMatDto> inMatListDao(SearchDto searchDto);
	
	//입고 리스트 조건별 조회
	public List<InMatDto> inMatOtherListDao(SearchDto searchDto);
	
	//납품 명세서 거래처 리스트
	public List<InMatDto> inMatDeliveryMasterDao(SearchDto searchDto);
	
	//납품 명세서 세부 리스트
	public List<InMatDto> inMatDeliverySubDao(SearchDto searchDto);
	
	//입고 반품 리스트
	public List<InMatDto> inMatReturnSelectDao(SearchDto searchDto);
}
