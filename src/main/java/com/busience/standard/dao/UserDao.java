package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.standard.dto.UserDto;

@Mapper
public interface UserDao {
	
	//유저 정보
	public UserDto selectUser(@Param("USER_CODE") String userCode);
	
	//유저 목록
	public List<UserDto> selectUserList();
	
	//유저 등록
	public int insertUser(UserDto userDto);
	
	//유저 등록시 메뉴관리 테이블에 메뉴리스트 추가
	public int insertMenuNewUser(@Param("userCode") String userCode, @Param("userType") String userType);
	
	//유저 수정
	public int updateUser(UserDto userDto);
	
	//비밀번호 수정
	public int updatePassword(UserDto userDto);

}
