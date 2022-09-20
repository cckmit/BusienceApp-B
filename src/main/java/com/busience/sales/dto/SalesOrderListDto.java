package com.busience.sales.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class SalesOrderListDto {

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
	private String Sales_Order_STND_2; // productSTND1
	private int Sales_SM_Last_Qty;
	private String Sales_Order_mCode; // 거래처코드
	private String Sales_Order_mName; // 거래처명
	private String Sales_Order_mDate; // ordermaster에서의 수주일자
	private String Sales_Order_mDlvry_Date; // ordermaster에서의 납기일자
	private String Sales_Order_CLSFC_1; // 품목분류 name
	private String Sales_Order_CLSFC_2; // 품목분류 name
}
