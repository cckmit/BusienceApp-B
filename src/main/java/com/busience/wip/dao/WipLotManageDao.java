package com.busience.wip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.wip.dto.WipLotTransDto;

@Mapper
public interface WipLotManageDao {
	
	//wipLotManageListDao
	public List<WipLotTransDto> wipLotManageListDao(SearchDto searchDto);
	
	//wipLastDataDao
	public List<WipLotTransDto> wipLastDataDao(SearchDto searchDto);
	
	//wipLotTransInsertDao
	public int wipLotTransInsertDao(WipLotTransDto wipLotTransDto);
	
	//wipLotMasterUpdateDao
	public int wipLotTransUpdateDao(WipLotTransDto wipLotTransDto);
	
	//wipLotManageListCountDao
	public int wipLotManageListCountDao(String wip_Create_Date);
	
	//wipLastDataDao
	public WipLotTransDto wipLastDataDao(String wip_LotNo);
	
	//wipInOutDao
	public List<WipLotTransDto> wipInOutListDao();
	
	//wipInputDao
	public List<WipLotTransDto> wipInputListDao(SearchDto searchDto);
	
	//wipInputDao
	public List<WipLotTransDto> wipOutputListDao(SearchDto searchDto);
	
	//wipLotMasterListDao
	public List<WipLotTransDto> wipLotMasterListDao(WipLotTransDto wipLotTransDto);
	
	//wipProcessingListDao
	public List<WipLotTransDto> wipProcessingListDao(WipLotTransDto wipLotTransDto);
	
	//wipLotTransDelete
	public int wipLotTransDeleteDao(WipLotTransDto wipLotTransDto);
}
