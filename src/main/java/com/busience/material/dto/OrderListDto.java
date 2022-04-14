package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderListDto {
	private int Order_lNo;
	private String Order_lCus_No; 
	private String Order_lCode;
	private String Order_lName;
	private String Order_STND_1;
	private int Order_lQty;
	private int Order_lSum;
	private int Order_lUnit_Price;
	private int Order_lPrice;
	private int Order_lNot_Stocked;
	private String Order_Rcv_Clsfc;
	private String Order_lInfo_Remark;
	private String Order_Dlvry_Date;
	private String Order_Product_Unit;
	private String Order_Rcv_Clsfc_Name;
}
