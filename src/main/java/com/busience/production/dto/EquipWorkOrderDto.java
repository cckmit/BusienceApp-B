package com.busience.production.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EquipWorkOrderDto {

	private String Equip_WorkOrder_Code;
	private String Equip_WorkOrder_Name;
	private String Equip_WorkOrder_ItemCode;
	private String Equip_WorkOrder_ItemName;
	
	private String Equip_WorkOrder_Old_ItemCode;
	private String Equip_WorkOrder_INFO_STND_1;
	private String Equip_WorkOrder_INFO_STND_2;
	private String Equip_WorkOrder_Material;
	private String Equip_WorkOrder_Material_Name;
	private String Equip_WorkOrder_Item_CLSFC_1;
	private String Equip_WorkOrder_Item_CLSFC_1_Name;
	private String Equip_WorkOrder_Item_CLSFC_2;
	private String Equip_WorkOrder_Item_CLSFC_2_Name;
	private String Equip_WorkOrder_Equipment_Type;
	
	// 작업지시 등록 프로그램에서 필요
	
	private String WorkOrder_EquipCode;
	private String WorkOrder_ItemCode;
	private String WorkOrder_SubCode;
	private String WorkOrder_SubName;
}
