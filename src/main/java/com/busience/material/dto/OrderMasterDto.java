package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderMasterDto {
	private String Order_mCus_No; 
	private String Order_mCode;
	private String Order_mName;
	private String Order_mDate;
	private int Order_mTotal;
	private String Order_mDlvry_Date;
	private String Order_mRemarks;
	private String Order_mModifier;
	private String Order_mModify_Date;
	private String Order_mCheck;
}
