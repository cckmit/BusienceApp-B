 package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.UserDao;
import com.busience.standard.dto.UserDto;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	// 코드 조건으로 조회
	public UserDto selectUser(String User_Code) {
        return userDao.selectUser(User_Code);
	}
	
	// 유저 목록
	public List<UserDto> selectUserList() {
        return userDao.selectUserList();
	}
	
	//회원가입 & 가입시 메뉴 저장
	public int userRegister(UserDto userDto) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					userDao.insertUser(userDto);
				
					userDao.insertMenuNewUser(userDto.getUser_Code(), userDto.getUser_Type());
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//회원정보수정
	public int userModify(UserDto userDto) {
		return userDao.updateUser(userDto);
	}

	//비밀번호 수정
	public int passwordModify(UserDto userDto) {
		return userDao.updatePassword(userDto);
	}
}
