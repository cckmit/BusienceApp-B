package com.busience.qc.dto;

public class OQCInspect_tbl {

	private String OQCInspect_No, OQCInspect_Lot_No, OQCInspect_INo, OQCInspect_Prcsn_Clsfc, OQCInspect_DQty,
			OQCInspect_PQty, OQCInspect_SQty, OQCInspect_SaQty, OQCInspect_Date, OQCInspect_Worker, OQCInspect_Problem,
			OQCInspect_OqcInNo;

	// 조인컬럼
	private String Cus_Code, Cus_Name, PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, remark,
			OQCInspectType_IQty;

	private String OQCInspect_Prcsn_Clsfc_Name, OQCInspect_Worker_Name;

	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOQCInspect_OqcInNo() {
		return OQCInspect_OqcInNo;
	}

	public void setOQCInspect_OqcInNo(String oQCInspect_OqcInNo) {
		OQCInspect_OqcInNo = oQCInspect_OqcInNo;
	}

	public String getOQCInspect_No() {
		return OQCInspect_No;
	}

	public void setOQCInspect_No(String oQCInspect_No) {
		OQCInspect_No = oQCInspect_No;
	}

	public String getOQCInspect_Lot_No() {
		return OQCInspect_Lot_No;
	}

	public void setOQCInspect_Lot_No(String oQCInspect_Lot_No) {
		OQCInspect_Lot_No = oQCInspect_Lot_No;
	}

	public String getOQCInspect_INo() {
		return OQCInspect_INo;
	}

	public void setOQCInspect_INo(String oQCInspect_INo) {
		OQCInspect_INo = oQCInspect_INo;
	}

	public String getOQCInspect_Prcsn_Clsfc() {
		return OQCInspect_Prcsn_Clsfc;
	}

	public void setOQCInspect_Prcsn_Clsfc(String oQCInspect_Prcsn_Clsfc) {
		OQCInspect_Prcsn_Clsfc = oQCInspect_Prcsn_Clsfc;
	}

	public String getOQCInspect_DQty() {
		return OQCInspect_DQty;
	}

	public void setOQCInspect_DQty(String oQCInspect_DQty) {
		OQCInspect_DQty = oQCInspect_DQty;
	}

	public String getOQCInspect_PQty() {
		return OQCInspect_PQty;
	}

	public void setOQCInspect_PQty(String oQCInspect_PQty) {
		OQCInspect_PQty = oQCInspect_PQty;
	}

	public String getOQCInspect_SQty() {
		return OQCInspect_SQty;
	}

	public void setOQCInspect_SQty(String oQCInspect_SQty) {
		OQCInspect_SQty = oQCInspect_SQty;
	}

	public String getOQCInspect_SaQty() {
		return OQCInspect_SaQty;
	}

	public void setOQCInspect_SaQty(String oQCInspect_SaQty) {
		OQCInspect_SaQty = oQCInspect_SaQty;
	}

	public String getOQCInspect_Date() {
		return OQCInspect_Date;
	}

	public void setOQCInspect_Date(String oQCInspect_Date) {
		OQCInspect_Date = oQCInspect_Date;
	}

	public String getOQCInspect_Worker() {
		return OQCInspect_Worker;
	}

	public void setOQCInspect_Worker(String oQCInspect_Worker) {
		OQCInspect_Worker = oQCInspect_Worker;
	}

	public String getOQCInspect_Problem() {
		return OQCInspect_Problem;
	}

	public void setOQCInspect_Problem(String oQCInspect_Problem) {
		OQCInspect_Problem = oQCInspect_Problem;
	}

	public String getCus_Code() {
		return Cus_Code;
	}

	public void setCus_Code(String cus_Code) {
		Cus_Code = cus_Code;
	}

	public String getCus_Name() {
		return Cus_Name;
	}

	public void setCus_Name(String cus_Name) {
		Cus_Name = cus_Name;
	}

	public String getPRODUCT_ITEM_CODE() {
		return PRODUCT_ITEM_CODE;
	}

	public void setPRODUCT_ITEM_CODE(String pRODUCT_ITEM_CODE) {
		PRODUCT_ITEM_CODE = pRODUCT_ITEM_CODE;
	}

	public String getPRODUCT_ITEM_NAME() {
		return PRODUCT_ITEM_NAME;
	}

	public void setPRODUCT_ITEM_NAME(String pRODUCT_ITEM_NAME) {
		PRODUCT_ITEM_NAME = pRODUCT_ITEM_NAME;
	}

	public String getPRODUCT_INFO_STND_1() {
		return PRODUCT_INFO_STND_1;
	}

	public void setPRODUCT_INFO_STND_1(String pRODUCT_INFO_STND_1) {
		PRODUCT_INFO_STND_1 = pRODUCT_INFO_STND_1;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOQCInspectType_IQty() {
		return OQCInspectType_IQty;
	}

	public void setOQCInspectType_IQty(String oQCInspectType_IQty) {
		OQCInspectType_IQty = oQCInspectType_IQty;
	}

	public String getOQCInspect_Prcsn_Clsfc_Name() {
		return OQCInspect_Prcsn_Clsfc_Name;
	}

	public void setOQCInspect_Prcsn_Clsfc_Name(String oQCInspect_Prcsn_Clsfc_Name) {
		OQCInspect_Prcsn_Clsfc_Name = oQCInspect_Prcsn_Clsfc_Name;
	}

	public String getOQCInspect_Worker_Name() {
		return OQCInspect_Worker_Name;
	}

	public void setOQCInspect_Worker_Name(String oQCInspect_Worker_Name) {
		OQCInspect_Worker_Name = oQCInspect_Worker_Name;
	}

	@Override
	public String toString() {
		return "OQCInspect_tbl [OQCInspect_No=" + OQCInspect_No + ", OQCInspect_Lot_No=" + OQCInspect_Lot_No
				+ ", OQCInspect_INo=" + OQCInspect_INo + ", OQCInspect_Prcsn_Clsfc=" + OQCInspect_Prcsn_Clsfc
				+ ", OQCInspect_DQty=" + OQCInspect_DQty + ", OQCInspect_PQty=" + OQCInspect_PQty + ", OQCInspect_SQty="
				+ OQCInspect_SQty + ", OQCInspect_SaQty=" + OQCInspect_SaQty + ", OQCInspect_Date=" + OQCInspect_Date
				+ ", OQCInspect_Worker=" + OQCInspect_Worker + ", OQCInspect_Problem=" + OQCInspect_Problem
				+ ", Cus_Code=" + Cus_Code + ", Cus_Name=" + Cus_Name + ", PRODUCT_ITEM_CODE=" + PRODUCT_ITEM_CODE
				+ ", PRODUCT_ITEM_NAME=" + PRODUCT_ITEM_NAME + ", PRODUCT_INFO_STND_1=" + PRODUCT_INFO_STND_1
				+ ", remark=" + remark + ", OQCInspectType_IQty=" + OQCInspectType_IQty
				+ ", OQCInspect_Prcsn_Clsfc_Name=" + OQCInspect_Prcsn_Clsfc_Name + ", OQCInspect_Worker_Name="
				+ OQCInspect_Worker_Name + "]";
	}
}
