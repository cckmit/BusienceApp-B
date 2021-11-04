package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.system.dao.CmnDao;

@Service
public class DtlService {
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	CmnDao cmnDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 코드 조건으로 조회
	public List<DtlDto> getAlldtl(int NEW_TBL_CODE) {
		final List<DtlDto> dtlList = dtlDao.findByCode(NEW_TBL_CODE);
        return dtlList;
	}
	
    // 현재 연월 조회 및 익월 조회
	public List<DtlDto> getDate(int NEW_TBL_CODE, String CHILD_TBL_NUM) {
		final List<DtlDto> PrceDate = dtlDao.findCodeNum(NEW_TBL_CODE, CHILD_TBL_NUM);
    	return PrceDate;
	}

	// 마지막 날짜 조회
	public List<DtlDto> getLastDay(int NEW_TBL_CODE) {
		final List<DtlDto> LastDay = dtlDao.findLastDay(NEW_TBL_CODE);
    	return LastDay;
	}

	// 부서명 조회
	public List<DtlDto> getDeptName(int NEW_TBL_CODE) {
		final List<DtlDto> DeptName = dtlDao.findDeptName(NEW_TBL_CODE);
    	return DeptName;
	}

	// 공통코드 저장
	public int dtlInsert(DtlDto dtlDto) {
		//사용자타입 (1) 을 생성했을 경우 권한관리 테이블에도 생성				
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					DtlDto numberList = dtlDao.findSaveNo(dtlDto.getNEW_TBL_CODE());
					String CHILD_TBL_NO_tmp = numberList.getCHILD_TBL_NO();
					String CHILD_TBL_NUM_tmp = numberList.getCHILD_TBL_NUM();
					
					dtlDto.setCHILD_TBL_NO(CHILD_TBL_NO_tmp);
					dtlDto.setCHILD_TBL_NUM(CHILD_TBL_NUM_tmp);
					
					//코드저장
					dtlDao.dtlInsertDao(dtlDto);
					
					if(dtlDto.getNEW_TBL_CODE() == 1) {
						//메뉴저장
						cmnDao.cmnInsertDao(CHILD_TBL_NUM_tmp);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	// 공통코드 수정
	public int dtlUpdate(DtlDto dtlDto) {
		return dtlDao.dtlUpdateDao(dtlDto);
	}
}
