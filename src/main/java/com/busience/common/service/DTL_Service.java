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
	
	// 코드 조건으로 조회
    public List<DTL_TBL> getAlldtl(String string) {
        final List<DTL_TBL> dtlList = dtl_Mapper.findByCode(string);
        return dtlList;
    }

}
