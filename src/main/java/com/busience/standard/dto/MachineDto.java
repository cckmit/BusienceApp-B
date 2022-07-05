package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MachineDto {

	private String EQUIPMENT_BUSINESS_PLACE;
	
	private String EQUIPMENT_BUSINESS_PLACE_NAME;
	
	private String EQUIPMENT_INFO_CODE;
	
	private String EQUIPMENT_INFO_NAME;
	
	private String EQUIPMENT_PACK_CODE;
	
	private String EQUIPMENT_PACK_NAME;
	
	private String EQUIPMENT_INFO_ABR;
	
	private Long EQUIPMENT_HEIGHT;
	
	private Long EQUIPMENT_WIDTH;
	
	private Long EQUIPMENT_DEPTH;
	
	private String EQUIPMENT_SERIAL_NUM;
	
	private Long EQUIPMENT_WEIGHT;
	
	private String EQUIPMENT_TYPE;
	
	private String EQUIPMENT_TYPE_NAME;
	
	private String EQUIPMENT_RECEIVED_D;
	
	private String EQUIPMENT_MODEL_YEAR;
	
	private String EQUIPMENT_MANUFACTURER;
	
	private String EQUIPMENT_STATUS;
	
	private String EQUIPMENT_STATUS_NAME;
	
	private String EQUIPMENT_INFO_RMARK;
	
	private String EQUIPMENT_USE_STATUS;
	
	private String EQUIPMENT_MODIFY_D;
	
	private String EQUIPMENT_MODIFIER;
	
	// 작업지시 등록 프로그램에서 필요
	
	private String WorkOrder_EquipCode;
	
	private String WorkOrder_EquipName;
	
	private String WorkOrder_SubCode;
	
	private String WorkOrder_SubName;
	
	private String WorkOrder_ONo;
	
	private String WorkOrder_ItemCode;
	
	private String WorkOrder_ItemName;
	
	private String WorkOrder_STND_1;
	
	private String WorkOrder_STND_2;
	
	private String WorkOrder_Unit;
	
	private String WorkOrder_Material;
	
	private String WorkOrder_MTRL_CLSFC;
	
	private String WorkOrder_Item_CLSFC_1;
	
	private String WorkOrder_Item_CLSFC_2;
	
	private String WorkOrder_RegisterTime;
	
	private String WorkOrder_Remark;
	
	private String WorkOrder_WorkStatus_Name;
	
	private String WorkOrder_Use_Status;
}
