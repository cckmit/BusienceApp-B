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
}
