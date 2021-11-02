package com.busience.system.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.system.dao.MenuMgmtDao;
import com.busience.system.dto.MenuMgmtDto;
import com.busience.system.service.MenuMgmtService;

@Service
public class MenuMgmtServiceImpl implements MenuMgmtService{

	@Autowired
	MenuMgmtDao menuMgmtDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//메뉴 리스트
	@Override
	public List<MenuMgmtDto> menuMgmtList(String userCode) {
        return menuMgmtDao.menuMgmtListDao(userCode);
	}

	//메뉴관리 수정
	@Override
	public int menuMgmtUpdate(List<MenuMgmtDto> jsonDataList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<jsonDataList.size();i++) {
						menuMgmtDao.menuMgmtUpdateDao(jsonDataList.get(i));
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
