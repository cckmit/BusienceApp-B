package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.standard.Dto.DTL_TBL;

@Mapper
public interface DTL_Mapper {
	
	@Select("select * from DTL_TBL")
	List<DTL_TBL> findAll();
	// 세부 테이블 정보 조회
	@Select("select * from DTL_TBL where NEW_TBL_CODE= #{NEW_TBL_CODE} order by CHILD_TBL_NUM*1")
	List<DTL_TBL> findByCode(@Param("NEW_TBL_CODE") String code);
	// 현재 연월 조회 및 익월 조회 - CHILD_TBL_RMARK
	@Select("select * from DTL_TBL where NEW_TBL_CODE= #{NEW_TBL_CODE} and CHILD_TBL_NUM= #{CHILD_TBL_NUM} and CHILD_TBL_USE_STATUS='true'")
	List<DTL_TBL> findCodeNum(@Param("NEW_TBL_CODE") String code, @Param("CHILD_TBL_NUM") String num);
	// 마지막 날짜 조회
	@Select("select CHILD_TBL_RMARK from DTL_TBL where NEW_TBL_CODE= #{NEW_TBL_CODE} limit 1")
	List<DTL_TBL> findLastDay(@Param("NEW_TBL_CODE") String code);
	// 부서명 조회하기 위한 쿼리
	@Select("select * from DTL_TBL where NEW_TBL_CODE= #{NEW_TBL_CODE} and CHILD_TBL_NUM > 0 and CHILD_TBL_USE_STATUS='true'")
	List<DTL_TBL> findDeptName(@Param("NEW_TBL_CODE") String code);
}
