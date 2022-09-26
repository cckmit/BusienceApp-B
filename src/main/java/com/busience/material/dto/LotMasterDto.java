package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LotMasterDto {

	private String LM_LotNo;
	private String LM_ItemCode;
	private int LM_Qty;
	private double LM_ChangeQty;
	private String LM_Create_Date;
	private String LM_Warehouse;
	private String LM_Warehouse_Name;
	
	private String LM_ClientCode;
	private String LM_ClientName;
	private double LM_TransQty;
	private double LM_ReturnQty;
	
	// 영업 
	private String LM_ItemName;
	private String LM_STND_1;
	private String LM_STND_2;
	private String LM_Item_Material;
	private String LM_Item_Material_Name;
	private String LM_Item_Unit;
	private String LM_Item_Unit_Name;
	private String LM_Item_CLSFC_1;
	private String LM_Item_CLSFC_1_Name;
	private String LM_Item_CLSFC_2;
	private String LM_Item_CLSFC_2_Name;
	
	// 품질
	private String LM_Inspect_Code;
}
