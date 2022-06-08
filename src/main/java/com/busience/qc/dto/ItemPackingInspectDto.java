package com.busience.qc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemPackingInspectDto {

	private int ItemPack_Inspect_Number;
	private String ItemPack_Inspect_LotNo;
	private String ItemPack_Inspect_ItemCode;
	private String ItemPack_Inspect_ItemName;
	private int ItemPack_Inspect_Qty;
	private String ItemPack_Inspect_Color;
	private String ItemPack_Inspect_Date;
	private String ItemPack_Inspect_Worker;
	private String ItemPack_Inspect_Value_1;
	private String ItemPack_Inspect_Value_2;
	private String ItemPack_Inspect_Value_3;
	private String ItemPack_Inspect_Value_4;
	private String ItemPack_Inspect_Value_5;
	private String ItemPack_Inspect_STND_1;
	private String ItemPack_Inspect_STND_2;
	private String ItemPack_Inspect_Status;
	private String ItemPack_Inspect_Result;
	private String ItemPack_Inspect_Package_Status;
	private String ItemPack_Inspect_Box_Status;
	private String ItemPack_Inspect_Packing_Unit;
	private String ItemPack_Inspect_Unit_1;
	
	private String ItemPack_Inspect_Worker_Name;
	
}
