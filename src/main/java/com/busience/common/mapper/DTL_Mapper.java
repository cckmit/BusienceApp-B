package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.standard.Dto.DTL_TBL;

@Mapper
public interface DTL_Mapper {
	
	@Select("select * from DTL_TBL")
	List<DTL_TBL> findAll();

	@Select("select * from DTL_TBL where NEW_TBL_CODE= #{NEW_TBL_CODE} order by CHILD_TBL_NUM*1")
	List<DTL_TBL> findByCode(@Param("NEW_TBL_CODE") String code);
}
