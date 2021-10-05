package com.busience.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.busience.common.mapper.DTL_Mapper;
import com.busience.standard.Dto.DTL_TBL;

@Service
public class DTL_Service {
	
	private DTL_Mapper dtl_Mapper;
	
	public DTL_Service(DTL_Mapper dtl_Mapper) {
		this.dtl_Mapper = dtl_Mapper;
	}
	
	// 멤버 전체 조회
    public List<DTL_TBL> getAlldtl(String string) {
        final List<DTL_TBL> dtlList = dtl_Mapper.findByCode(string);
        return dtlList;
    }
    
    // 현재 연월 조회 및 익월 조회
    public List<DTL_TBL> getDate(String code, String num) {
    	final List<DTL_TBL> PrceDate = dtl_Mapper.findCodeNum(code, num);
    	return PrceDate;
    }
    
    // 마지막 날짜 조회
    public List<DTL_TBL> getLastDay(String code) {
    	final List<DTL_TBL> LastDay = dtl_Mapper.findLastDay(code);
    	return LastDay;
    }
    
    // 부서명 조회
    public List<DTL_TBL> getDeptName(String code) {
    	final List<DTL_TBL> DeptName = dtl_Mapper.findDeptName(code);
    	return DeptName;
    }
    
}
