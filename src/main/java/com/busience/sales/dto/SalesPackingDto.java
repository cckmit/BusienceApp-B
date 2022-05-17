package com.busience.sales.dto;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@ToString
public class SalesPackingDto {

	private int Sales_Packing_No;
	private String Sales_Large_Packing_LotNo;
	private String Sales_Small_Packing_LotNo;
	private int Sales_Packing_Qty;
	private String Sales_Large_Packing_Time;
	private String Sales_Packing_Code;
	private String Sales_Packing_Name;
	private String Sales_Small_Create_Date;
	private String Sales_Packing_Status;
	private String Sales_Packing_STND_1;
	private String Sales_Packing_STND_2;
	private String Sales_Packing_Item_Clsfc_1;
	private String Sales_Packing_Status_Name;
	private String Sales_Packing_Unit;
	private String Sales_Packing_Return_Date;
	private String Sales_Packing_Modifier;
}
