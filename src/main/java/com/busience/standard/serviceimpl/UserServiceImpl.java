package com.busience.standard.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.UserDao;
import com.busience.standard.dto.UserDto;
import com.busience.standard.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	// 코드 조건으로 조회
	@Override
	public UserDto selectUser(String string) {
        return userDao.selectUser(string);
	}
	
	// 유저 목록
	@Override
	public List<UserDto> selectUserList() {
        return userDao.selectUserList();
	}
	
	//회원가입 & 가입시 메뉴 저장
	@Override
	public int userRegister(UserDto userDto) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					userDao.insertUser(userDto);
					
					userDao.insertMenuNewUser(userDto.getUSER_CODE());
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	//회원정보수정
	@Override
	public int userModify(UserDto userDto) {
		return userDao.updateUser(userDto);
	}

	//회원정보수정
	@Override
	public int passwordModify(UserDto userDto) {
		return userDao.updatePassword(userDto);
	}
}
