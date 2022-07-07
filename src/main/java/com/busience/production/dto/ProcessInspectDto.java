package com.busience.production.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessInspectDto {

	private int Process_Inspect_Number;
	private String Process_Inspect_LotNo;
	private String Process_Inspect_EquipCode;
	private String Process_Inspect_EquipName;
	private String Process_Inspect_ItemCode;
	private String Process_Inspect_ItemName;
	private int Process_Inspect_Qty;
	private String Process_Inspect_Color;
	private String Process_Inspect_Date;
	private String Process_Inspect_Worker;
	private String Process_Inspect_Value_1;
	private String Process_Inspect_Value_2;
	private String Process_Inspect_Value_3;
	private String Process_Inspect_Value_4;
	private String Process_Inspect_Value_5;
	private String Process_Inspect_STND_1;
	private String Process_Inspect_STND_2;
	private String Process_Inspect_Status;
	private String Process_Inspect_Result;
	private String Process_Inspect_Remark;
	
	private String Process_Inspect_STND_NAME_1;
	private String Process_Inspect_Item_Clsfc_Name_1;
	private String Process_Inspect_Create_Date;
	private String Process_Inspect_Worker_Name;
	private double Process_Inspect_CrateLot_Qty;
	
}
