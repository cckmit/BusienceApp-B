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
	private String LM_Create_Date;
	
	private String LM_ClientCode;
	private String LM_ClientName;
	
	// 영업 
	private String LM_ItemName;
	private String LM_STND_1;
	private String LM_Item_CLSFC_1;
}
