package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StockDto {
	private String S_ItemCode; //재고코드
	private String S_ItemName; //재고명
	private String S_Item_Standard_1; //규격1
	private String S_Item_Standard_2; //규격2
	private double S_Qty; // SM_Output_Qty - SM_Input_Qty 당월 재고량
	private String S_WareHouse; //처리연월
}
