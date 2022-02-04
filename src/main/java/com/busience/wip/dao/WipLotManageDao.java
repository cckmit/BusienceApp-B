package com.busience.wip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.DefectDto;
import com.busience.wip.dto.WipLotMasterDto;
import com.busience.wip.dto.WipLotTransDto;

@Mapper
public interface WipLotManageDao {
	
	//wipLotManageListDao
	public List<WipLotTransDto> wipLotManageListDao(SearchDto searchDto);
	
	//wipLotManageListDao
	public List<WipLotTransDto> wipLotTransListDao(SearchDto searchDto);
	
	//wipLastDataDao
	public List<WipLotTransDto> wipLastDataDao(SearchDto searchDto);
	
	//wipLotMasterInsertDao
	public int wipLotMasterInsertDao(WipLotMasterDto wipLotMasterDto);
	//wipLotTransInsertDao
	public int wipLotTransInsertDao(WipLotTransDto wipLotTransDto);
	
	//wipLotMasterUpdateDao
	public int wipLotTransUpdateDao(WipLotTransDto wipLotTransDto);
	
	//wipLotManageListCountDao
	public int wipLotManageListCountDao();
	
	//wipLastDataDao
	public WipLotTransDto wipLastDataDao(String wip_LotNo);
	
	//wipInOutDao
	public List<WipLotTransDto> wipInOutListDao();
	
	//wipInputDao
	public List<WipLotTransDto> wipInputListDao(SearchDto searchDto);
	
	//wipInputDao
	public List<WipLotTransDto> wipOutputListDao(SearchDto searchDto);
	
	//wipDefectSelectDao
	public List<DefectDto> wipDefectSelectDao(String wip_LotNo);
	
	//wipDefectInsertDao
	public int wipDefectInsertDao(DefectDto defectDto);
	
	//wipLotMasterListDao
	public List<WipLotTransDto> wipLotMasterListDao(WipLotTransDto wipLotTransDto);
	
	//wipProcessingListDao
	public List<WipLotTransDto> wipProcessingListDao(WipLotTransDto wipLotTransDto);
	
	//wipLotTransDelete
	public int wipLotTransDeleteDao(WipLotTransDto wipLotTransDto);
}
