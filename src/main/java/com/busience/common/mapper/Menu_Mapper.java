package com.busience.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Menu_Mapper {

	@Insert("insert into MENU_MGMT_TBL\r\n"
			+ "select #{userCode}, CHILD_TBL_NO, 'true', 'true', 'true', 'true'\r\n"
			+ "from DTL_TBL where NEW_TBL_CODE = '13'")
	int insertMenuNewUser(@Param("userCode") String userCode);
}
