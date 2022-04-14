package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface MatOutputDao {
	
	//LotMaster 조회
	public List<LotMasterDto> LotMasterSelectDao(SearchDto searchDto);
	
	//LotMaster 등록
	public int LotMasterInsertDao(OutMatDto outMatDto);
	
	//LotMaster 업데이트
	public int LotMasterUpdateDao(OutMatDto outMatDto);
	
	//LotTrans 번호 가져오기
	public int LotTransNoSelectDao(OutMatDto outMatDto);
	
	//LotTrans 저장
	public int LotTransInsertDao(OutMatDto outMatDto);
	
	//입고테이블 등록
	public int OutMatInsertDao(OutMatDto outMatDto);
	
	//요청sub 업데이트
	public int RequestSubUpdateDao(OutMatDto outMatDto);
	
	//재고테이블 수정
	public int StockInsertDao(OutMatDto outMatDto);
	
	//재고테이블 수정
	public int StockUpdateDao(OutMatDto outMatDto);
	
	//요청master
	public int RequestMasterUpdateDao(OutMatDto outMatDto);
	
	//출고조회
	public List<OutMatDto> matOutputListDao(SearchDto searchDto);
	
	//출고 조건별 조회
	public List<OutMatDto> matOutputOtherListDao(SearchDto searchDto);
	
	//부서별 명세서 master
	public List<OutMatDto> matOutputDeliveryMasterDao(SearchDto SearchDto);
	
	//부서별 명세서 sub
	public List<OutMatDto> matOutputDeliverySubDao(SearchDto SearchDto);
	
	//출고 반품 리스트
	public List<OutMatDto> matOutReturnSelectDao(SearchDto searchDto);
}
