package com.busience.salesLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sales_InMat_tbl {
	private Integer ID;
	private Integer Sales_InMat_No;
	private String Sales_InMat_Order_No;
	private String Sales_InMat_Lot_No;
	private String Sales_InMat_Code;
	private String Sales_InMat_OQC_No;
	private int Sales_InMat_Qty;
	private int Sales_InMat_Unit_Price;
	private int sales_InMat_Price;
	private String Sales_InMat_Date;
	private String Sales_InMat_Rcv_Clsfc;
	private String Sales_InMat_dInsert_Time;
	private String Sales_InMat_Modifier;
	
	private String Sales_InMat_Name; //제품명
	private String Sales_InMat_STND_1; //규격
	private int Sales_InReturn_Qty; //반품수량
	private String Sales_InMat_UNIT; //단위
	private String Sales_InMat_Rcv_Clsfc_Name;
	private String Sales_InMat_Item_Clsfc_1;
	private String Sales_InMat_WareHouse; // 창고
	private String Sales_InMat_Before;
	private String Sales_InMat_After;
}
