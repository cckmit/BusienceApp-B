package com.busience.salesLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesOrderMasterDto {
	private String Sales_Order_mCus_No;
	private String Sales_Order_mCode;
	private String Sales_Order_mName;
	private String Sales_Order_mDate;
	private int Sales_Order_mTotal;
	private String Sales_Order_mDlvry_Date;
	private String Sales_Order_mRemarks;
	private String Sales_Order_mCheck;
	private String Sales_Order_mModifier;
	private String Sales_Order_mModify_Date;
}
