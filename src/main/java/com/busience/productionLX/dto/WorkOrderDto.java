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
	private String NewWorkOrder_ONo;
	private String WorkOrder_ItemCode;
	private String WorkOrder_ItemName;
	private String WorkOrder_EquipCode;
	private String WorkOrder_EquipName;
	private String WorkOrder_PackEquipCode;
	private String WorkOrder_PackEquipName;
	private String WorkOrder_INFO_STND_1;
	private String WorkOrder_INFO_STND_2;
	private String WorkOrder_Item_STND_1;
	private String WorkOrder_Item_STND_2;
	private int WorkOrder_Item_Multiple;
	private String WorkOrder_WorkStatus;
	private String WorkOrder_WorkStatus_Name;
	private int WorkOrder_SQty;
	private int WorkOrder_PQty; //구 목표수량
	private int WorkOrder_RQty; //구 생산수량
	private int WorkOrder_ProductionQty; // 생산수량
	private int WorkOrder_AllottedQty; // 목표수량
	private int WorkOrder_DQty;
	private String WorkOrder_RegisterTime;
	private String WorkOrder_OrderTime;
	private String WorkOrder_ReceiptTime;
	private String WorkOrder_StartTime;
	private String WorkOrder_CompleteOrderTime;
	private String WorkOrder_CompleteTime;
	private String WorkOrder_Worker;
	private String WorkOrder_Remark;
	private boolean WorkOrder_Use_Status;
	private String EQUIPMENT_INFO_CODE;
	private String EQUIPMENT_INFO_NAME;
	private String EQUIPMENT_PACK_CODE;
	private String EQUIPMENT_PACK_NAME;
}
