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
}
