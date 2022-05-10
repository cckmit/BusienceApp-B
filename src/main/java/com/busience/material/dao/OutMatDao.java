package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface OutMatDao {
	
	//입고테이블 등록
	public int outMatInsertDao(OutMatDto outMatDto);
	
	//입고테이블 등록
	public int outMatInsertDao2(
			@Param("RequestNo") String RequestNo,
			@Param("LotNo") String LotNo, @Param("ItemCode") String ItemCode,
			@Param("Qty") Double Qty, @Param("DeptCode") String DeptCode,
			@Param("classfy") String classfy, @Param("Modifier") String Modifier
			);
	
	//출고조회
	public List<OutMatDto> outMatListDao(SearchDto searchDto);
	
	//출고 조건별 조회
	public List<OutMatDto> outMatOtherListDao(SearchDto searchDto);
	
	//부서별 명세서 master
	public List<OutMatDto> outMatDeliveryMasterDao(SearchDto SearchDto);
	
	//부서별 명세서 sub
	public List<OutMatDto> outMatDeliverySubDao(SearchDto SearchDto);
	
	//출고 반품 리스트
	public List<OutMatDto> outMatReturnSelectDao(SearchDto searchDto);
}
