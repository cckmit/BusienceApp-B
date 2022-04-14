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
	private String S_Item_Classfy_1; //분류1
	private String S_Item_Classfy_1_Name; //분류1
	private String S_Item_Classfy_2; //분류2
	private String S_Item_Classfy_2_Name; //분류2
	private double S_Qty; // 재고
	private String S_WareHouse; //창고
}
