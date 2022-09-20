package com.busience.sales.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesOutMatDto {
	private Integer Sales_OutMat_No;
	private String Sales_OutMat_Cus_No;
	private String Sales_OutMat_Lot_No;
	private String Sales_OutMat_Code;
	private int Sales_OutMat_Qty;
	private int Sales_OutMat_Unit_Price;
	private int Sales_OutMat_Price;
	private String Sales_OutMat_Client_Code;
	private String Sales_OutMat_Client_Name;
	private String Sales_OutMat_Date;
	private String Sales_OutMat_Send_Clsfc;
	private String Sales_OutMat_dInsert_Time;
	private String Sales_OutMat_Modifier;

	// add column
	private String Sales_OutMat_STND_1;
	private String Sales_OutMat_STND_2;
	private String Sales_OutMat_UNIT;
	private String Sales_OutMat_Name; // 제품명
	private int Sales_OutReturn_Qty; // 판매 반품
	private String Sales_OutMat_Send_Clsfc_Name; // 출고 구분
	private String Sales_OutMat_Before;
	private String Sales_OutMat_After;
	private String Sales_OutMat_WareHouse;
	private String Sales_OutMat_Order_No;
	private int LT_No; // 랫트랜스 번호
	private String Sales_OutMat_Material; // 재질
	private String Sales_OutMat_Item_Clsfc_Name_1; // 품목 분류1
	private String Sales_OutMat_Item_Clsfc_Name_2; // 품목 분류2
}
