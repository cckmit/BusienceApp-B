package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.salesLX.dto.SalesOrderMasterDto;

@Mapper
public interface WorkOrderDao {
	
	///workOrderSelectDao
	public List<WorkOrderDto> workOrderSelectDao(SearchDto searchDto);
	
	public List<WorkOrderDto> workOrderSubSelectDao(SearchDto searchDto);

	public int workOrderCountDao(WorkOrderDto workOrderDto);

	public List<WorkOrderDto> workOrderChoiceSelectDao(SearchDto searchDto);
	
	public int workOrderNoSelectDao();
	
	public List<WorkOrderDto> workOrderCompleteSelectDao(SearchDto searchDto);
	
	//작업 현황
	public List<WorkOrder_tbl> workListSearch(SearchDto searchDto);
	
	//세부 작업 현황
	public List<WorkOrder_tbl> workdListSearch(SearchDto searchDto);
	
	//작업지시 접수 상단 그리드
	public List<WorkOrderDto> workorderListSelect(SearchDto searchDto);
	
	//작업지시 접수 직압 흐 reload
	public WorkOrderDto workorderUpdatedList(WorkOrderDto workOrderDto);
	
	public int workOrderSumQtyDao(SearchDto searchDto);
	
	public int workOrderRegisterDao(WorkOrderDto workOrderDto);
	
	public int workOrderUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderStockUpdateDao(WorkOrderDto workOrderDto);
	
	//작업지시 접수 작업 update 
	public int workorderlistUpdate(@Param("WorkOrder_ONo") String WorkOrder_ONo, @Param("WorkStatus") String WorkStatus, @Param("modifier") String modifier);
	
	public int workOrderQtyUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderDeleteDao(WorkOrderDto workOrderDto);
	
	public int lastProductQtyDao(ProductionMgmtDto productionMgmtDto);
	
	public int lastProductModifyDao(ProductionMgmtDto productionMgmtDto);
	
	public List<SalesOrderMasterDto> workOrderSalesOrderSelectDao(SearchDto searchDto);
	
	public List<WorkOrderDto> workingSelectByMachineDao(SearchDto searchDto);

	//상태 변경
	public int workOrderStatusUpdateDao(WorkOrderDto workOrderDto);
}
