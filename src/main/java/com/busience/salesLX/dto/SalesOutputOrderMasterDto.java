package com.busience.salesLX.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class SalesOutputOrderMasterDto {
	
	private String Sales_Output_Order_mOrder_No;
	private String Sales_Output_Order_mCus_No;
	private String Sales_Output_Order_mCode;
	private String Sales_Output_Order_mDate;
	private int Sales_Output_Order_mTotal;
	private String Sales_Output_Order_mDlvry_Date;
	private String Sales_Output_Order_mRemarks;
	private String Sales_Output_Order_mCheck;
	private String Sales_Output_Order_mModifier;
	private String Sales_Output_Order_mModify_Date;
	
}
