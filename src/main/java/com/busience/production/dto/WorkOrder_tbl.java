package com.busience.production.dto;

public class WorkOrder_tbl {

	private String WorkOrder_No, WorkOrder_ONo, WorkOrder_ItemCode, WorkOrder_EquipCode, WorkOrder_PQty, WorkOrder_RQty,
			WorkOrder_StartTime, WorkOrder_RegisterTime, WorkOrder_ReceiptTime, WorkOrder_OrderTime,
			WorkOrder_CompleteOrderTime, WorkOrder_CompleteTime, WorkOrder_WorkStatus, WorkOrder_Worker,
			WorkOrder_Remark;

	private String WorkOrder_ItemName, WorkOrder_EquipName, WorkOrder_WorkStatusName, PRODUCT_INFO_STND_1,
			PRODUCT_UNIT_PRICE, Qty, percent, OQCInspect_DQty;

	private String dbdata_flag;

	public String getWorkOrder_StartTime() {
		return WorkOrder_StartTime;
	}

	public void setWorkOrder_StartTime(String workOrder_StartTime) {
		WorkOrder_StartTime = workOrder_StartTime;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getOQCInspect_DQty() {
		return OQCInspect_DQty;
	}

	public void setOQCInspect_DQty(String oQCInspect_DQty) {
		OQCInspect_DQty = oQCInspect_DQty;
	}

	public String getWorkOrder_EquipName() {
		return WorkOrder_EquipName;
	}

	public void setWorkOrder_EquipName(String workOrder_EquipName) {
		WorkOrder_EquipName = workOrder_EquipName;
	}

	public String getWorkOrder_EquipCode() {
		return WorkOrder_EquipCode;
	}

	public void setWorkOrder_EquipCode(String workOrder_EquipCode) {
		WorkOrder_EquipCode = workOrder_EquipCode;
	}

	public String getWorkOrder_No() {
		return WorkOrder_No;
	}

	public void setWorkOrder_No(String workOrder_No) {
		WorkOrder_No = workOrder_No;
	}

	public String getDbdata_flag() {
		return dbdata_flag;
	}

	public void setDbdata_flag(String dbdata_flag) {
		this.dbdata_flag = dbdata_flag;
	}

	public String getQty() {
		return Qty;
	}

	public void setQty(String qty) {
		Qty = qty;
	}

	public String getWorkOrder_WorkStatusName() {
		return WorkOrder_WorkStatusName;
	}

	public void setWorkOrder_WorkStatusName(String workOrder_WorkStatusName) {
		WorkOrder_WorkStatusName = workOrder_WorkStatusName;
	}

	public String getWorkOrder_ONo() {
		return WorkOrder_ONo;
	}

	public void setWorkOrder_ONo(String workOrder_ONo) {
		WorkOrder_ONo = workOrder_ONo;
	}

	public String getWorkOrder_ItemCode() {
		return WorkOrder_ItemCode;
	}

	public void setWorkOrder_ItemCode(String workOrder_ItemCode) {
		WorkOrder_ItemCode = workOrder_ItemCode;
	}

	public String getWorkOrder_PQty() {
		return WorkOrder_PQty;
	}

	public void setWorkOrder_PQty(String workOrder_PQty) {
		WorkOrder_PQty = workOrder_PQty;
	}

	public String getWorkOrder_RQty() {
		return WorkOrder_RQty;
	}

	public void setWorkOrder_RQty(String workOrder_RQty) {
		WorkOrder_RQty = workOrder_RQty;
	}

	public String getWorkOrder_RegisterTime() {
		return WorkOrder_RegisterTime;
	}

	public void setWorkOrder_RegisterTime(String workOrder_RegisterTime) {
		WorkOrder_RegisterTime = workOrder_RegisterTime;
	}

	public String getWorkOrder_ReceiptTime() {
		return WorkOrder_ReceiptTime;
	}

	public void setWorkOrder_ReceiptTime(String workOrder_ReceiptTime) {
		WorkOrder_ReceiptTime = workOrder_ReceiptTime;
	}

	public String getWorkOrder_OrderTime() {
		return WorkOrder_OrderTime;
	}

	public void setWorkOrder_OrderTime(String workOrder_OrderTime) {
		WorkOrder_OrderTime = workOrder_OrderTime;
	}

	public String getWorkOrder_CompleteOrderTime() {
		return WorkOrder_CompleteOrderTime;
	}

	public void setWorkOrder_CompleteOrderTime(String workOrder_CompleteOrderTime) {
		WorkOrder_CompleteOrderTime = workOrder_CompleteOrderTime;
	}

	public String getWorkOrder_CompleteTime() {
		return WorkOrder_CompleteTime;
	}

	public void setWorkOrder_CompleteTime(String workOrder_CompleteTime) {
		WorkOrder_CompleteTime = workOrder_CompleteTime;
	}

	public String getWorkOrder_WorkStatus() {
		return WorkOrder_WorkStatus;
	}

	public void setWorkOrder_WorkStatus(String workOrder_WorkStatus) {
		WorkOrder_WorkStatus = workOrder_WorkStatus;
	}

	public String getWorkOrder_Worker() {
		return WorkOrder_Worker;
	}

	public void setWorkOrder_Worker(String workOrder_Worker) {
		WorkOrder_Worker = workOrder_Worker;
	}

	public String getWorkOrder_Remark() {
		return WorkOrder_Remark;
	}

	public void setWorkOrder_Remark(String workOrder_Remark) {
		WorkOrder_Remark = workOrder_Remark;
	}

	public String getWorkOrder_ItemName() {
		return WorkOrder_ItemName;
	}

	public void setWorkOrder_ItemName(String workOrder_ItemName) {
		this.WorkOrder_ItemName = workOrder_ItemName;
	}

	public String getPRODUCT_INFO_STND_1() {
		return PRODUCT_INFO_STND_1;
	}

	public void setPRODUCT_INFO_STND_1(String pRODUCT_INFO_STND_1) {
		PRODUCT_INFO_STND_1 = pRODUCT_INFO_STND_1;
	}

	public String getPRODUCT_UNIT_PRICE() {
		return PRODUCT_UNIT_PRICE;
	}

	public void setPRODUCT_UNIT_PRICE(String pRODUCT_UNIT_PRICE) {
		PRODUCT_UNIT_PRICE = pRODUCT_UNIT_PRICE;
	}
}
