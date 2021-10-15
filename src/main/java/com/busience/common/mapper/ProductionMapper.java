package com.busience.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.busience.common.domain.Production;
import com.busience.productionLX.dto.WorkOrder_tbl;

@Mapper
public interface ProductionMapper {

	@Insert("INSERT INTO `PRODUCTION_MGMT_TBL2`\r\n"
			+ "(`PRODUCTION_WorkOrder_ONo`,\r\n"
			+ "`PRODUCTION_Equipment_Code`,"
			+ "`PRODUCTION_Volume`)\r\n"
			+ "VALUES\r\n"
			+ "(#{PRODUCTION_WorkOrder_ONo},\r\n"
			+ "#{PRODUCTION_Equipment_Code},\r\n"
			+ "#{PRODUCTION_Volume})")
	int insertProduction(Production production);
	
	@Select("SELECT A.WorkOrder_ONo, A.WorkOrder_EquipCode, B.CHILD_TBL_TYPE FROM WorkOrder_tbl A\r\n"
			+ "inner join DTL_TBL B on A.WorkOrder_WorkStatus = B.CHILD_TBL_NO\r\n"
			+ "where A.WorkOrder_EquipCode = #{equip_id} and B.CHILD_TBL_RMARK = 'S'")
	List<WorkOrder_tbl> selectWorkOrder(@Param("equip_id") String string);
}
