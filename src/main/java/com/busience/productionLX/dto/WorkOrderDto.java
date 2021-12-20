package com.busience.productionLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkOrderDto {

	private int WorkOrder_No;
	private String WorkOrder_ONo;
	private String WorkOrder_ItemCode;
	private String WorkOrder_ItemName;
	private String WorkOrder_EquipCode;
	private String WorkOrder_EquipName;
	private String workOrder_Item_STND_1;
	private String WorkOrder_WorkStatus;
	private String WorkOrder_WorkStatus_Name;
	private int WorkOrder_PQty;
	private int WorkOrder_RQty;
	private int WorkOrder_DQty;
	private String WorkOrder_RegisterTime;
	private String WorkOrder_OrderTime;
	private String WorkOrder_ReceiptTime;
	private String WorkOrder_StartTime;
	private String WorkOrder_CompleteOrderTime;
	private String WorkOrder_CompleteTime;
	private String WorkOrder_Worker;
	private String WorkOrder_Remark;
}
