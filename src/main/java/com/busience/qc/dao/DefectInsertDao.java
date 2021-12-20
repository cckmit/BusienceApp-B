package com.busience.qc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dto.DefectDto;

@Mapper
public interface DefectInsertDao {
	
	//완료된 작업지시 내역
	public List<WorkOrderDto> completeWorkOrderDao(SearchDto searchDto);
	
	public List<DefectDto> defectInfoDao();
	
	public List<DefectDto> defectListDao(String Defect_ONo);
	
	public int defectSaveDao(DefectDto defectDto);
}
