package com.busience.salesLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesOutputOrderListDto {

	private int Sales_Output_Order_lNo;
	private String Sales_Output_Order_lOrder_No;
	private String Sales_Output_Order_lCode;
	private int Sales_Output_Order_lQty;
	private int Sales_Output_Order_lSum;
	private int Sales_Output_Order_lUnit_Price;
	private int Sales_Output_Order_lPrice;
	private int Sales_Output_Order_lNot_Stocked;
	private String Sales_Output_Order_Send_Clsfc;
	private String Sales_Output_Order_lInfo_Remark;
	// 추가
	private String Sales_Output_Order_lName;
	private String Sales_Order_STND_1;
	private String Sales_Order_CLSFC_1;
	private String Sales_Output_Order_mCus_No;
}
