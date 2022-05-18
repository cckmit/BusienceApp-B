package com.busience.tablet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrateLotDto {

	private String CL_LotNo;
	private String CL_OrderNo;
	private String CL_ItemCode;
	private String CL_ItemName;
	private String CL_CrateCode;
	private double CL_Qty;
	private String CL_Create_Date;
	private boolean CL_Use_Status;
	
	private String CL_Production_ID;
	private String CL_Before_LotNo;
}