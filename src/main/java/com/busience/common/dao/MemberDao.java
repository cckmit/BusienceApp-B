package com.busience.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.MemberDto;

@Mapper
public interface MemberDao {
	
	//유저 목록
	public MemberDto viewDao(@Param("USER_CODE") String userCode);
	
	//유저 등록
	public int insertUser(MemberDto member);
	
	//유저 등록시 메뉴관리 테이블에 메뉴리스트 추가
	public int insertMenuNewUser(@Param("userCode") String userCode);
	
	//유저 수정
	public int updateUser(MemberDto member);
	
	//비밀번호 수정
	public int updatePassword(MemberDto member);

}
