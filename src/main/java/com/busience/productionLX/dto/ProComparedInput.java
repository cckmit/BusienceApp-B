package com.busience.productionLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProComparedInput {

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
	
	private int WorkOrder_InputPQty; // 투입대비 생산실적 지시량
	private int WorkOrder_InputRQty; // 투입대비 생산실적 생산량
	private int Total_RQty; // 총생산량
	private String Last_Month; // 자재 출고 지난 달 1일 값 가져오기위함
	private int OutMat_Count; // 출고일자별 갯수 세기
	private String WorkOrder_ItemName, WorkOrder_EquipName, WorkOrder_WorkStatusName, PRODUCT_INFO_STND_1,
	PRODUCT_UNIT_PRICE, Qty, percent, OQCInspect_DQty;
	
	private Integer ID;
	private Integer OutMat_No;
	private String OutMat_ReqNo;
	private String OutMat_Lot_No;
	private String OutMat_Code;
	private int OutMat_Qty;
	private String OutMat_Dept_Code;
	private String OutMat_Consignee; // 수취인
	private String OutMat_Send_Clsfc;
	private String OutMat_Date;
	private String OutMat_dInsert_Time;
	private String OutMat_Modifier;

	private String OutMat_Dept_Name;
	private String OutMat_Name;
	private String OutMat_Send_Clsfc_Name;
	private String OutMat_Consignee_Name;
	private int OutReturn_Qty;

	private String OutMat_STND_1;
	private String OutMat_UNIT;
	private String OutMat_Info_Remark;
}
