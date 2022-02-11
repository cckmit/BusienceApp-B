package com.busience.qc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dto.DefectDto;

@Mapper
public interface DefectListDao {
	
	//불량합계
	public List<WorkOrderDto> defectListSelectDao(SearchDto searchDto);
	
	//제품별 불량합계
	public List<WorkOrderDto> defectItemListSelectDao(SearchDto searchDto);
	
	//설비별 불량합계
	public List<WorkOrderDto> defectMachineListSelectDao(SearchDto searchDto);
	
	//불량세부정보
	public List<DefectDto> defectListSubSelectDao(SearchDto searchDto);
}
