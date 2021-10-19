package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.common.domain.Member;

@Mapper
public interface MemberMapper {
	
	//유저 목록
	@Select("select * from USER_INFO_TBL where USER_CODE = #{USER_CODE}")
	List<Member> findByCode(@Param("USER_CODE") String code);

}
