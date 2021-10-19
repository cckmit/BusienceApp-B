package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.common.domain.Menu;

@Mapper
public interface MenuMapper {
	
	//메뉴 목록
	@Select("select * from Menu_tbl")
	List<Menu> selectMenuList();
	
	//유저 등록시 메뉴관리 테이블에 메뉴리스트 추가
	@Insert("insert into Menu_MGMT_tbl\r\n"
			+ "select #{userCode}, CHILD_TBL_NO, 'true', 'true', 'true', 'true'\r\n"
			+ "from DTL_TBL where NEW_TBL_CODE = '13'")
	int insertMenuNewUser(@Param("userCode") String userCode);
}
