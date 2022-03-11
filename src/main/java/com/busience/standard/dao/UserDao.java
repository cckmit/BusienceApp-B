package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.standard.dto.UserDto;

@Mapper
public interface UserDao {
	
	//유저 정보
	public UserDto selectUser(String User_Code);
	
	//유저 목록
	public List<UserDto> selectUserList();
	
	//유저 등록
	public int insertUser(UserDto userDto);
	
	//유저 등록시 메뉴관리 테이블에 메뉴리스트 추가
	public int insertMenuNewUser(String User_Code, String User_Type);
	
	//유저 수정
	public int updateUser(UserDto userDto);
	
	//비밀번호 수정
	public int updatePassword(UserDto userDto);

}
