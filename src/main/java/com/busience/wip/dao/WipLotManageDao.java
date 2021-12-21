package com.busience.wip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.wip.dto.WipLotMasterDto;
import com.busience.wip.dto.WipLotTransDto;

@Mapper
public interface WipLotManageDao {
	
	//wipLotManageListDao
	public List<WipLotMasterDto> wipLotManageListDao(SearchDto searchDto);
	
	//wipLotManageInsertDao
	public int wipLotMasterInsertDao(WipLotMasterDto wipLotManageDto);
	
	//wipLotMasterUpdateDao
	public int wipLotMasterUpdateDao(WipLotMasterDto wipLotMasterDto);
	
	//wipLotTransInsertDao
	public int wipLotTransInsertDao(WipLotTransDto wipLotTransDto);
	
	//wipLotMasterUpdateDao
	public int wipLotTransUpdateDao(WipLotTransDto wipLotTransDto);
	
	//wipLotManageListCountDao
	public int wipLotManageListCountDao(String wip_Create_Date);
	
	//wipInOutDao
	public List<WipLotTransDto> wipInOutListDao();
	
	//wipInputDao
	public List<WipLotTransDto> wipInputListDao(SearchDto searchDto);
	
	//wipInputDao
	public List<WipLotTransDto> wipOutputListDao(SearchDto searchDto);
	
	//wipLotMasterListDao
	public List<WipLotMasterDto> wipLotMasterListDao(SearchDto searchDto);
	
	//wipProcessingListDao
	public List<WipLotMasterDto> wipProcessingListDao(String endProcessCode);
	
	//wipLotTransDelete
	public int wipLotTransDeleteDao(WipLotTransDto wipLotTransDto);
	
	//wipInputRollbackDao
	public int wipInputRollbackDao(WipLotTransDto wipLotTransDt);
	
	//wipOutputRollbackDao
	public int wipOutputRollbackDao(WipLotTransDto wipLotTransDt);
}
