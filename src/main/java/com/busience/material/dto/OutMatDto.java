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
	private int OM_Qty;
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
}
