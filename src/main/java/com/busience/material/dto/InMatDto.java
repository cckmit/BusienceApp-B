package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InMatDto {
	private Integer InMat_No;
	private String InMat_Order_No;
	private String InMat_Lot_No;
	private String InMat_Code;
	private int InMat_Qty;
	private int InMat_Unit_Price;
	private int InMat_Price;
	private String InMat_Client_Code;
	private String InMat_Date;
	private String InMat_Rcv_Clsfc;
	private String InMat_dInsert_Time;
	private String InMat_Modifier;

	private String InMat_Name; // 제품명
	private String InMat_STND_1; // 규격
	private String InMat_STND_2; // 규격
	private int InReturn_Qty; // 반품수량
	private int InMat_Stock_Qty; // 재고수량
	private String InMat_UNIT; // 단위
	private String InMat_Client_Name;
	private String InMat_Rcv_Clsfc_Name;
	private String InMat_Info_Remark;
	private String PRODUCT_INFO_STND_1;
	private String PRODUCT_UNIT;
	private String InMat_WareHouse;
	private String InMat_Save_Area;
	
	private String InMat_Before;
	private String InMat_After;
}
