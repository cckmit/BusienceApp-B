package com.busience.sales.dto;

public class Sales_OrderList_tbl {
	private int ID;
	private int Sales_Order_lNo;
	private String Sales_Order_lCus_No;
	private String Sales_Order_lCode;
	private int Sales_Order_lQty;
	private int Sales_Order_lSum;
	private int Sales_Order_lUnit_Price;
	private int Sales_Order_lPrice;
	private int Sales_Order_lNot_Stocked;
	private String Sales_Order_Send_Clsfc;
	private String Sales_Order_lInfo_Remark;
	private String Sales_Order_lStatus; // 발주상태

	// add column
	private String Sales_Order_lName; // productName
	private String Sales_Order_STND_1; // productSTND1
	private int Sales_SM_Last_Qty;
	private String Sales_Order_mCode; // 거래처코드
	private String Sales_Order_mName; // 거래처명
	private String Sales_Order_mDate; // ordermaster에서의 수주일자
	private String Sales_Order_mDlvry_Date; // ordermaster에서의 납기일자

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getSales_Order_lNo() {
		return Sales_Order_lNo;
	}

	public void setSales_Order_lNo(int sales_Order_lNo) {
		Sales_Order_lNo = sales_Order_lNo;
	}

	public String getSales_Order_lCus_No() {
		return Sales_Order_lCus_No;
	}

	public void setSales_Order_lCus_No(String sales_Order_lCus_No) {
		Sales_Order_lCus_No = sales_Order_lCus_No;
	}

	public String getSales_Order_lCode() {
		return Sales_Order_lCode;
	}

	public void setSales_Order_lCode(String sales_Order_lCode) {
		Sales_Order_lCode = sales_Order_lCode;
	}

	public int getSales_Order_lQty() {
		return Sales_Order_lQty;
	}

	public void setSales_Order_lQty(int sales_Order_lQty) {
		Sales_Order_lQty = sales_Order_lQty;
	}

	public int getSales_Order_lSum() {
		return Sales_Order_lSum;
	}

	public void setSales_Order_lSum(int sales_Order_lSum) {
		Sales_Order_lSum = sales_Order_lSum;
	}

	public int getSales_Order_lUnit_Price() {
		return Sales_Order_lUnit_Price;
	}

	public void setSales_Order_lUnit_Price(int sales_Order_lUnit_Price) {
		Sales_Order_lUnit_Price = sales_Order_lUnit_Price;
	}

	public int getSales_Order_lPrice() {
		return Sales_Order_lPrice;
	}

	public void setSales_Order_lPrice(int sales_Order_lPrice) {
		Sales_Order_lPrice = sales_Order_lPrice;
	}

	public int getSales_Order_lNot_Stocked() {
		return Sales_Order_lNot_Stocked;
	}

	public void setSales_Order_lNot_Stocked(int sales_Order_lNot_Stocked) {
		Sales_Order_lNot_Stocked = sales_Order_lNot_Stocked;
	}

	public String getSales_Order_Send_Clsfc() {
		return Sales_Order_Send_Clsfc;
	}

	public void setSales_Order_Send_Clsfc(String sales_Order_Send_Clsfc) {
		Sales_Order_Send_Clsfc = sales_Order_Send_Clsfc;
	}

	public String getSales_Order_lInfo_Remark() {
		return Sales_Order_lInfo_Remark;
	}

	public void setSales_Order_lInfo_Remark(String sales_Order_lInfo_Remark) {
		Sales_Order_lInfo_Remark = sales_Order_lInfo_Remark;
	}

	public String getSales_Order_lStatus() {
		return Sales_Order_lStatus;
	}

	public void setSales_Order_lStatus(String sales_Order_lStatus) {
		Sales_Order_lStatus = sales_Order_lStatus;
	}

	public String getSales_Order_lName() {
		return Sales_Order_lName;
	}

	public void setSales_Order_lName(String sales_Order_lName) {
		Sales_Order_lName = sales_Order_lName;
	}

	public String getSales_Order_STND_1() {
		return Sales_Order_STND_1;
	}

	public void setSales_Order_STND_1(String sales_Order_STND_1) {
		Sales_Order_STND_1 = sales_Order_STND_1;
	}

	public int getSales_SM_Last_Qty() {
		return Sales_SM_Last_Qty;
	}

	public void setSales_SM_Last_Qty(int sales_SM_Last_Qty) {
		Sales_SM_Last_Qty = sales_SM_Last_Qty;
	}

	public String getSales_Order_mCode() {
		return Sales_Order_mCode;
	}

	public void setSales_Order_mCode(String sales_Order_mCode) {
		Sales_Order_mCode = sales_Order_mCode;
	}

	public String getSales_Order_mName() {
		return Sales_Order_mName;
	}

	public void setSales_Order_mName(String sales_Order_mName) {
		Sales_Order_mName = sales_Order_mName;
	}

	public String getSales_Order_mDate() {
		return Sales_Order_mDate;
	}

	public void setSales_Order_mDate(String sales_Order_mDate) {
		Sales_Order_mDate = sales_Order_mDate;
	}

	public String getSales_Order_mDlvry_Date() {
		return Sales_Order_mDlvry_Date;
	}

	public void setSales_Order_mDlvry_Date(String sales_Order_mDlvry_Date) {
		Sales_Order_mDlvry_Date = sales_Order_mDlvry_Date;
	}

	@Override
	public String toString() {
		return "Sales_OrderList_tbl [ID=" + ID + ", Sales_Order_lNo=" + Sales_Order_lNo + ", Sales_Order_lCus_No="
				+ Sales_Order_lCus_No + ", Sales_Order_lCode=" + Sales_Order_lCode + ", Sales_Order_lQty="
				+ Sales_Order_lQty + ", Sales_Order_lSum=" + Sales_Order_lSum + ", Sales_Order_lUnit_Price="
				+ Sales_Order_lUnit_Price + ", Sales_Order_lPrice=" + Sales_Order_lPrice + ", Sales_Order_lNot_Stocked="
				+ Sales_Order_lNot_Stocked + ", Sales_Order_Send_Clsfc=" + Sales_Order_Send_Clsfc
				+ ", Sales_Order_lInfo_Remark=" + Sales_Order_lInfo_Remark + ", Sales_Order_lStatus="
				+ Sales_Order_lStatus + ", Sales_Order_lName=" + Sales_Order_lName + ", Sales_Order_STND_1="
				+ Sales_Order_STND_1 + ", Sales_SM_Last_Qty=" + Sales_SM_Last_Qty + ", Sales_Order_mCode="
				+ Sales_Order_mCode + ", Sales_Order_mName=" + Sales_Order_mName + ", Sales_Order_mDate="
				+ Sales_Order_mDate + ", Sales_Order_mDlvry_Date=" + Sales_Order_mDlvry_Date + "]";
	}
}
