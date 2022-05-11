package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemporaryStorageDto {
	private String TS_OrderNo;
	private String TS_ItemCode;
	private String TS_ItemName;
	private double TS_Qty; 
	private int TS_Unit_Price;
	private int TS_Price;
	private String TS_Client_Code;
	private String TS_Client_Name;
	private String TS_Date;
	private String TS_Classfy;
	private String TS_Classfy_Name;
	private String TS_Insert_Time;
	private String TS_Modifier;
}
