 package com.busience.standard.service;

import java.util.List;

import com.busience.standard.dto.UserDto;

public interface UserService {

	// 코드 조건으로 조회
    public UserDto selectUser(String string);
    
    // 리스트
    public List<UserDto> selectUserList();
    
    //회원가입 & 가입시 메뉴 저장
    public int userRegister(UserDto userDto);
    
    //회원정보수정
    public int userModify(UserDto userDto);
    
    //회원정보수정
    public int passwordModify(UserDto userDto);
}
