package com.busience.production.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.sales.dto.SalesOrderMasterDto;

@Mapper
public interface WorkOrderDao {
	
	public List<WorkOrderDto> workOrderSelectDao(SearchDto searchDto);	

	public int workOrderNoSelectDao();
	
	public int workOrderRegisterDao(WorkOrderDto workOrderDto);

	public int workOrderDeleteDao(WorkOrderDto workOrderDto);

	public List<WorkOrderDto> workListSelectDao(SearchDto searchDto);

	public int workListSaveDao(WorkOrderDto workOrderDto);
	
	public WorkOrderDto workOrderOneSelectDao(String orderNo);
	
	public int workOrderStartCheckDao(String machineCode);
	
	public List<WorkOrderDto> workDetailListSearch(SearchDto searchDto);
	
	//--------------------------------------	
	
	public List<WorkOrderDto> workOrderSubSelectDao(SearchDto searchDto);

	public int workOrderCountDao(WorkOrderDto workOrderDto);

	public List<WorkOrderDto> workOrderChoiceSelectDao(SearchDto searchDto);
	
	
	public List<WorkOrderDto> workOrderCompleteSelectDao(SearchDto searchDto);
	
	//작업 현황
	public List<WorkOrderDto> workListSearch(SearchDto searchDto);
	
	//세부 작업 현황
	public List<WorkOrderDto> workdListSearch(SearchDto searchDto);
	
	//작업지시 접수 상단 그리드
	public List<WorkOrderDto> workorderListSelect(SearchDto searchDto);
	
	//작업지시 접수 직압 흐 reload
	public WorkOrderDto workorderUpdatedList(WorkOrderDto workOrderDto);
	
	//공정 검사 조회(생산된 제품 조회)
	public List<ProductionMgmtDto> workOrderResultDao(SearchDto searchDto);
		
	public int workOrderSumQtyDao(SearchDto searchDto);
	
	
	public int workOrderUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderStockUpdateDao(WorkOrderDto workOrderDto);
	
	//작업지시 접수 작업 update 
	public int workorderlistUpdate(@Param("WorkOrder_ONo") String WorkOrder_ONo, @Param("WorkStatus") String WorkStatus, @Param("modifier") String modifier);
	
	public int workOrderQtyUpdateDao(WorkOrderDto workOrderDto);
	
	
	public int lastProductQtyDao(ProductionMgmtDto productionMgmtDto);
	
	public int lastProductModifyDao(ProductionMgmtDto productionMgmtDto);
	
	public List<SalesOrderMasterDto> workOrderSalesOrderSelectDao(SearchDto searchDto);
	
	public List<WorkOrderDto> workingSelectByMachineDao(SearchDto searchDto);

	//상태 변경
	public int workOrderStatusUpdateDao(WorkOrderDto workOrderDto);
	
	public List<WorkOrderDto> workOrderMonitoringSelectDao(SearchDto searchDto);
	
	//작업종료
	public int workOrderFinalUpdate(WorkOrderDto workOrderDto);
}
