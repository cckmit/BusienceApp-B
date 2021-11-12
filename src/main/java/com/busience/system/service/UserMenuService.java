package com.busience.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.system.dao.UserMenuDao;
import com.busience.system.dto.UserMenuDto;

@Service
public class UserMenuService {

	@Autowired
	UserMenuDao userMenuDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//유저메뉴 리스트
	public List<UserMenuDto> userMenuList(String User_Code) {
        return userMenuDao.userMenuListDao(User_Code);
	}

	//유저메뉴 저장
	public int userMenuInsert(List<UserMenuDto> UserMenuDtoList, String User_Code) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<UserMenuDtoList.size();i++) {
						UserMenuDtoList.get(i).setUser_Code(User_Code);
						userMenuDao.userMenuInsertDao(UserMenuDtoList.get(i));
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//유저메뉴 삭제
	public int userMenuDelete(List<UserMenuDto> UserMenuDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<UserMenuDtoList.size();i++) {

						userMenuDao.userMenuDeleteDao(UserMenuDtoList.get(i));
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
