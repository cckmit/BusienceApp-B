package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutMatDto {

	private Integer OM_No;
	private String OM_RequestNo;
	private String OM_LotNo;
	private String OM_ItemCode;
	private double OM_Qty;
	private double OM_ReturnQty;
	private String OM_DeptCode;
	private String OM_Send_Clsfc;
	private String OM_OutDate;
	private String OM_Create_Time;
	private String OM_Modifier;

	private String OM_DeptName;
	private String OM_ItemName;
	private String OM_Send_Clsfc_Name;
	private String OM_Before;
	private String OM_After;
	private String OM_WareHouse;
	private String OM_WareHouse_Name;
	
	private String OM_Item_Stnd_1;
	private String OM_Item_Stnd_2;
	private String OM_Item_CLSFC_1;
	private String OM_Item_CLSFC_1_Name;
	private String OM_Item_CLSFC_2;
	private String OM_Item_CLSFC_2_Name;
	private String OM_Unit;
	
}
