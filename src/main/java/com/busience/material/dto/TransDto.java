package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransDto {

	private int T_No;
	private String T_LotNo;
	private String T_ItemCode;
	private String T_ItemName;
	private String T_Item_Stnd_1;
	private String T_Item_Stnd_2;
	private String T_ITEM_CLSFC_1;
	private String T_ITEM_CLSFC_2;
	private int T_Qty;
	
	private String T_Before; 
	private String T_After;
	private String T_Before_Name; 
	private String T_After_Name;
	private String T_Create_Date;
	
	private String T_Classify;
	private String T_Classify_Name;
	private String T_Modifier;
	private String T_WareHouse;
	
}
