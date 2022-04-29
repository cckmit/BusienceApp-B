package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

@Mapper
public interface LotMasterDao {
	
	//랏마스터 insert update
	public int lotMasterInsertUpdateDao(InMatDto inMatDto);
	
	//랏마스터조회
	public List<LotMasterDto> lotMasterSelectDao(SearchDto searchDto);
	
	//랏마스터 insert
	public int lotMasterInsertDao(OutMatDto outMatDto);
	
	//랏마스터 update
	public int lotMasterUpdateDao(@Param("LM_Qty") Double LM_Qty, @Param("LM_LotNo") String LM_LotNo);
	
	//영업 LotMaster insert
	public int salesLotMasterInsertDao(Sales_InMat_tbl sales_InMat_tbl);
	
	//영업 LotMaster 조회
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto);
	
	//영업 입고 LotMaster 조회
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto);
	
}