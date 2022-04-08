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
	private String WorkOrder_PQty; // 구 목료수량
	private String WorkOrder_RQty; // 구 생산수량
	private String WorkOrder_ProductionQty; // 생산수량
	private String WorkOrder_AllottedQty; // 목표수량
	private String WorkOrder_StartTime;
	private String WorkOrder_StartTime2;
	private String WorkOrder_RegisterTime;
	private String WorkOrder_RegisterTime2;
	private String WorkOrder_CompleteTime;
	private String WorkOrder_CompleteTime2;
	private String WorkOrder_WorkStatus;
	private String WorkOrder_Worker;
	private String WorkOrder_Remark;
	private boolean WorkOrder_Use_Status;
	
	private int WorkOrder_InputPQty; // 투입대비 생산실적 지시량
	private int WorkOrder_InputRQty; // 투입대비 생산실적 생산량

	private String WorkOrder_ItemName, WorkOrder_EquipName, WorkOrder_WorkStatusName, PRODUCT_INFO_STND_1,
	        Product_Item_CLSFC_1,  Product_Item_CLSFC_2, Product_Item_CLSFC_NAME_1, Product_Item_CLSFC_NAME_2, PRODUCT_UNIT_PRICE, Qty, percent, OQCInspect_DQty;

	private String dbdata_flag;
}
