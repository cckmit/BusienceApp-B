package com.busience.tablet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrateDto {

	private String C_CrateCode;
	private String C_Condition;
	private String C_Condition_Name;
	private String C_Create_Date;
	private boolean C_Use_Status;
	private String C_Production_LotNo;

	private String C_Before_CrateCode;
	private String C_ItemCode;
	private double C_Qty;
	private double C_ProductionQty;

	private String C_MachineCode;
	private String C_MachineName;
	private String C_MachineCode2;
	private String C_MachineName2;
	private String CL_Create_Date;
	private String CL_Update_Date;
}

