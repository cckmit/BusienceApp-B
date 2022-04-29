package com.busience.tablet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RawMaterialMasterDto {

	private String RMM_OrderNo;
	private String RMM_Production_ID;
	private String RMM_ItemCode;
	private double RMM_Qty;
	private String RMM_Create_Date;
	
	private String RMM_Before_Production_ID;
}
