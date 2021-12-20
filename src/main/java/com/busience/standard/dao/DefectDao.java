package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.DefectInfoDto;

@Mapper
public interface DefectDao {

	//조회
	public List<DefectInfoDto> selectDefectListDao();
	
	//등록
	public int insertDefectDao(DefectInfoDto DefectDto);
	
	//수정
	public int updateDefectDao(DefectInfoDto DefectDto);
	
	//삭제
	public int deleteDefectDao(String Cus_Code);
	
	//특정 거래처 조회
	public DefectInfoDto selectDefectDao(String Cus_Code);
}
