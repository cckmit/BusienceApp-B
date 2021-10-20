package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.busience.common.domain.Member;

@Mapper
public interface MemberMapper {
	
	//유저 목록
	@Select("select * from USER_INFO_TBL where USER_CODE = #{USER_CODE}")
	List<Member> findByCode(@Param("USER_CODE") String code);
	
	//유저 등록
	@Insert("insert into USER_INFO_TBL (\r\n"
			+ "USER_CODE, USER_PASSWORD,\r\n"
			+ "USER_NAME, USER_TYPE,\r\n"
			+ "COMPANY, DEPT_CODE,\r\n"
			+ "USER_MODIFIER, USER_USE_STATUS\r\n"
			+ ") values(\r\n"
			+ "#{USER_CODE}, #{USER_PASSWORD},\r\n"
			+ "#{USER_NAME}, #{USER_TYPE},\r\n"
			+ "#{COMPANY}, #{DEPT_CODE},\r\n"
			+ "#{USER_MODIFIER}, #{USER_USE_STATUS}\r\n"
			+ ")")
	int insertUser(Member member);
	
	//유저 수정
	@Update("update USER_INFO_TBL\r\n"
			+ "set\r\n"
			+ "USER_NAME = #{USER_NAME},\r\n"
			+ "USER_TYPE = #{USER_TYPE},\r\n"
			+ "COMPANY = #{COMPANY},\r\n"
			+ "DEPT_CODE = #{DEPT_CODE},\r\n"
			+ "USER_MODIFIER = #{USER_MODIFIER},\r\n"
			+ "USER_MODIFY_D = now(),\r\n"
			+ "USER_USE_STATUS = #{USER_USE_STATUS}\r\n"
			+ "where USER_CODE = #{USER_CODE}")
	int updateUser(Member member);
	
	//비밀번호 수정
	@Update("update USER_INFO_TBL\r\n"
			+ "set\r\n"
			+ "USER_PASSWORD = #{USER_PASSWORD},\r\n"
			+ "USER_MODIFIER = #{USER_MODIFIER},\r\n"
			+ "USER_MODIFY_D = now()\r\n"
			+ "where USER_CODE = #{USER_CODE}")
	int updatePassword(Member member);

}
