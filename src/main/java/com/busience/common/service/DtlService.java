package com.busience.common.service;

import java.util.List;

import com.busience.common.dto.DtlDto;

public interface DtlService {
	
	// 코드 조건으로 조회
    public List<DtlDto> getAlldtl(int NEW_TBL_CODE);
    
    // 현재 연월 조회 및 익월 조회
    public List<DtlDto> getDate(int NEW_TBL_CODE, String CHILD_TBL_NUM);
    
    // 마지막 날짜 조회
    public List<DtlDto> getLastDay(int NEW_TBL_CODE);
    
    // 부서명 조회
    public List<DtlDto> getDeptName(int NEW_TBL_CODE);
    
 	//저장
 	public int dtlInsert(DtlDto dtlDto);
 	
 	//삭제
 	public int dtlUpdate(DtlDto dtlDto);
}
