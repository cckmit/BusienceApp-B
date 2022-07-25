package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;

@Mapper
public interface LotMasterDao {
	
	//랏마스터 insert update
	public int lotMasterInsertUpdateDao(
			@Param("LM_LotNo") String LM_LotNo, @Param("LM_ItemCode") String LM_ItemCode,
			@Param("LM_Qty") Double LM_Qty, @Param("LM_Warehouse") String LM_Warehouse
			);
	
	//랏마스터조회
	public List<LotMasterDto> lotMasterMatSelectDao(SearchDto searchDto);
	
	//랏마스터조회
	public List<LotMasterDto> lotMasterSelectDao(SearchDto searchDto);
	
	//랏마스터 insert
	public int lotMasterInsertDao(OutMatDto outMatDto);
	
	//랏마스터 update
	public int lotMasterUpdateDao(@Param("LM_Qty") Double LM_Qty, @Param("LM_LotNo") String LM_LotNo, @Param("LM_Warehouse") String LM_Warehouse);
	
	//영업 LotMaster insert
	public int salesLotMasterInsertUpdateDao(
			@Param("LM_LotNo") String LM_LotNo, @Param("LM_ItemCode") String LM_ItemCode,
			@Param("LM_Qty") Double LM_Qty, @Param("LM_Warehouse") String LM_Warehouse
			);
	
	//영업 LotMaster 조회
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto);
	
	//영업 입고 LotMaster 조회
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto);
	
	//제품 포장 LotMaster 조회
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto);
	
}
