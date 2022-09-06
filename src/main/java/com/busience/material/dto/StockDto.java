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
	private String S_Warehouse; //창고
	private String S_Warehouse_Name;
	private double S_Sales_Output_Order_Qty; // 현재지시수량
	private String S_Item_Unit; // 단위
	private String S_Item_Unit_Name; // 단위
	private String S_Item_Material; // 재질
	private String S_Item_Material_Name; // 재질
	private String S_LotNo; // 재고조정에서 필요
	private double S_ChangeQty; // 변경수량
	private double S_AddQty; // 변경수량
	private double S_ReturnQty;
}
