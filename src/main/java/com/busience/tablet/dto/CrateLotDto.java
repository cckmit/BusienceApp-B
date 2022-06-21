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

	private String CL_MachineCode;
	private String CL_Production_ID;
	private String CL_Before_LotNo;
	private String CL_EquipCode;
	private String CL_EquipName;
	private String CL_STND_1;
	private String CL_Item_Clsfc_Name_1;
	private String CL_Item_Clsfc_Name_2;
	private String CL_Item_Material;
	private String CL_Process_Inspect_Qty;
}