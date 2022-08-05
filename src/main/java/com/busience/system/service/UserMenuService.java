package com.busience.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.system.dao.UserMenuDao;
import com.busience.system.dto.UserMenuDto;

@Service
public class UserMenuService {

	@Autowired
	UserMenuDao userMenuDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//메뉴 리스트
	public List<UserMenuDto> allMenuList(String User_Code) {
        return userMenuDao.allMenuListDao(User_Code);
	}
	
	//유저메뉴 리스트
	public List<UserMenuDto> userMenuList(String User_Code) {
        return userMenuDao.userMenuListDao(User_Code);
	}

	//유저메뉴 저장
	public int userMenuInsert(UserMenuDto userMenuDto, String User_Code) {
		userMenuDto.setUser_Code(User_Code);
		return userMenuDao.userMenuInsertDao(userMenuDto);
	}
	
	//유저메뉴 삭제
	public int userMenuDelete(UserMenuDto userMenuDto, String User_Code) {
		userMenuDto.setUser_Code(User_Code);
		return userMenuDao.userMenuDeleteDao(userMenuDto);
	}
}
