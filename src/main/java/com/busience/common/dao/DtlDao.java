package com.busience.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.DtlDto;

@Mapper
public interface DtlDao {
	
	public List<DtlDto> findAllByCode(@Param("NEW_TBL_CODE") int NEW_TBL_CODE);
	
	// 세부 테이블 정보 조회
	public List<DtlDto> findByCode(@Param("NEW_TBL_CODE") int NEW_TBL_CODE);
	
	// 현재 연월 조회 및 익월 조회 - CHILD_TBL_RMARK
	public List<DtlDto> findCodeNum(@Param("NEW_TBL_CODE") int NEW_TBL_CODE, @Param("CHILD_TBL_NUM") String CHILD_TBL_NUM);
	
	// 마지막 날짜 조회
	public List<DtlDto> findLastDay(@Param("NEW_TBL_CODE") int NEW_TBL_CODE);
	
	// 부서명 조회하기 위한 쿼리
	public List<DtlDto> findDeptName(@Param("NEW_TBL_CODE") int NEW_TBL_CODE);
	
	// 저장하기위한 다음 번호 찾기
	public DtlDto findSaveNo(int NEW_TBL_CODE);
	
	// 저장
	public int dtlInsertDao(DtlDto dtlDto);
	
	// 삭제
	public int dtlUpdateDao(DtlDto dtlDto);
}
