package com.busience.productionLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkOrder_tbl {

	private String WorkOrder_No;
	private String WorkOrder_ONo;
	private String WorkOrder_ItemCode;
	private String WorkOrder_EquipCode;
	private String WorkOrder_PQty;
	private String WorkOrder_RQty;
	private String WorkOrder_StartTime;
	private String WorkOrder_RegisterTime;
	private String WorkOrder_ReceiptTime;
	private String WorkOrder_OrderTime;
	private String WorkOrder_CompleteOrderTime;
	private String WorkOrder_CompleteTime;
	private String WorkOrder_WorkStatus;
	private String WorkOrder_Worker;
	private String WorkOrder_Remark;

	private String WorkOrder_ItemName, WorkOrder_EquipName, WorkOrder_WorkStatusName, PRODUCT_INFO_STND_1,
			PRODUCT_UNIT_PRICE, Qty, percent, OQCInspect_DQty;

	private String dbdata_flag;
}
