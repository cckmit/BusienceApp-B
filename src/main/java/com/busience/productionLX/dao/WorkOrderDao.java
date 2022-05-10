package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
	
	public int workOrderSumQtyDao(SearchDto searchDto);
	
	public int workOrderRegisterDao(WorkOrderDto workOrderDto);
	
	public int workOrderUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderStockUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderQtyUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderDeleteDao(WorkOrderDto workOrderDto);
	
	public int lastProductQtyDao(ProductionMgmtDto productionMgmtDto);
	
	public int lastProductModifyDao(ProductionMgmtDto productionMgmtDto);
	
	public List<SalesOrderMasterDto> workOrderSalesOrderSelectDao(SearchDto searchDto);
	
	public List<WorkOrderDto> workingSelectByMachineDao(SearchDto searchDto);

	//상태 변경
	public int workOrderStatusUpdateDao(WorkOrderDto workOrderDto);
}
