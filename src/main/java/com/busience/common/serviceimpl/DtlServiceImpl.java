package com.busience.common.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.service.DtlService;

@Service
public class DtlServiceImpl implements DtlService{

	@Autowired
	DtlDao dtlDao;
	
	// 코드 조건으로 조회
	@Override
	public List<DtlDto> getAlldtl(int NEW_TBL_CODE) {
		final List<DtlDto> dtlList = dtlDao.findByCode(NEW_TBL_CODE);
        return dtlList;
	}
	
    // 현재 연월 조회 및 익월 조회
	@Override
	public List<DtlDto> getDate(int NEW_TBL_CODE, String CHILD_TBL_NUM) {
		final List<DtlDto> PrceDate = dtlDao.findCodeNum(NEW_TBL_CODE, CHILD_TBL_NUM);
    	return PrceDate;
	}

	// 마지막 날짜 조회
	@Override
	public List<DtlDto> getLastDay(int NEW_TBL_CODE) {
		final List<DtlDto> LastDay = dtlDao.findLastDay(NEW_TBL_CODE);
    	return LastDay;
	}

	// 부서명 조회
	@Override
	public List<DtlDto> getDeptName(int NEW_TBL_CODE) {
		final List<DtlDto> DeptName = dtlDao.findDeptName(NEW_TBL_CODE);
    	return DeptName;
	}

	@Override
	public int dtlInsert(DtlDto dtlDto) {
		DtlDto numberList = dtlDao.findSaveNo(dtlDto.getNEW_TBL_CODE());
		String CHILD_TBL_NO_tmp = numberList.getCHILD_TBL_NO();
		String CHILD_TBL_NUM_tmp = numberList.getCHILD_TBL_NUM();
		
		dtlDto.setCHILD_TBL_NO(CHILD_TBL_NO_tmp);
		dtlDto.setCHILD_TBL_NUM(CHILD_TBL_NUM_tmp);
				
		return dtlDao.dtlInsertDao(dtlDto);
	}

	@Override
	public int dtlUpdate(DtlDto dtlDto) {
		return dtlDao.dtlUpdateDao(dtlDto);
	}
    
}
