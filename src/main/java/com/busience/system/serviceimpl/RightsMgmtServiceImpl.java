package com.busience.system.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.system.dao.RightsMgmtDao;
import com.busience.system.dto.RightsMgmtDto;
import com.busience.system.service.RightsMgmtService;

@Service
public class RightsMgmtServiceImpl implements RightsMgmtService{

	@Autowired
	RightsMgmtDao rightsMgmtDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//메뉴 리스트
	@Override
	public List<RightsMgmtDto> rightsMgmtList(String userType) {
        return rightsMgmtDao.rightsMgmtListDao(userType);
	}

	@Override
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
			System.out.println(e);
			return 0;
		}
	}
	
}
