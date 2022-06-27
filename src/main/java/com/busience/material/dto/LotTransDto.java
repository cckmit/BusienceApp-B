package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LotTransDto {

	private int LT_No;
	private String LT_LotNo;
	private String LT_ItemCode;
	private String LT_ItemName;
	private String LT_Item_Stnd_1;
	private String LT_Item_Stnd_2;
	private String LT_ITEM_CLSFC_1;
	private String LT_ITEM_CLSFC_2;
	private int LT_Qty;
	private int LT_InQty;
	private int LT_InReturn_Qty;
	private int LT_InOther_Qty;
	private int LT_OutQty;
	private int LT_OutReturn_Qty;
	private int LT_OutOther_Qty;
	private String LT_Before; 
	private String LT_After;
	private String LT_Create_Date;
	
	private String LT_Classify;
	private String LT_Classify_Name;
	private String LT_Modifier;
	private String LT_WareHouse;
	private String LT_ITEM_MATERIAL;
	
}
