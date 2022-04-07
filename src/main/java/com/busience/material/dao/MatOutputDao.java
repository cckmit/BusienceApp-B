package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockMat_tbl;

@Mapper
public interface MatOutputDao {
	
	//LotMaster 조회
	public List<LotMasterDto> LotMasterSelectDao(SearchDto searchDto);
	
	//조회
	public List<StockMat_tbl> OutMatListDao();
	
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
	public int StockUpdateDao(OutMatDto outMatDto);
	
	//요청master
	public int RequestMasterUpdateDao(OutMatDto outMatDto);
}
