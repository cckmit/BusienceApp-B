package com.busience.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.system.dao.RightsMgmtDao;
import com.busience.system.dto.RightsMgmtDto;

@Service
public class RightsMgmtService {

	@Autowired
	RightsMgmtDao rightsMgmtDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//권한관리 리스트
	public List<RightsMgmtDto> rightsMgmtList(String userType) {
        return rightsMgmtDao.rightsMgmtListDao(userType);
	}

	//권환관리 수정
	public int rightsMgmtUpdate(List<RightsMgmtDto> jsonDataList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<jsonDataList.size();i++) {
						rightsMgmtDao.rightsMgmtUpdateDao(jsonDataList.get(i));
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
