package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.CrateDto;

@Mapper
public interface CrateDao {

	//조회
	public List<CrateDto> crateSelectDao(CrateDto crateDto);
	
	//조회
	public CrateDto crateStatusCheckDao(SearchDto searchDto);
	
	//조회
	public CrateDto crateSelectByMachineDao(SearchDto searchDto);
	
	//랏번호 조회
	public String crateLotNoSelectDao(CrateDto crateDto);
	
	//저장
	public int crateSaveDao(CrateDto crateDto);
	
	//수정
	public int crateUpdateDao(CrateDto crateDto);
}
