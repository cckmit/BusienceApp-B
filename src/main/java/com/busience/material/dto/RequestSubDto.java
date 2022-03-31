package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestSubDto {

	private Integer RS_No;
	private String RS_RequestNo;
	private String RS_ItemCode;
	private String RS_ItemName;
	private double RS_Qty;
	private double RS_Sum;
	private double RS_Not_Stocked;
	private String RS_Send_Clsfc;
	private String RS_Remark;
	
	private double RS_Stock_Qty;
	private String RS_Send_Clsfc_Name;
}
