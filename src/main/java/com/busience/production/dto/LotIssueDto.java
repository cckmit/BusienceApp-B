package com.busience.production.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class LotIssueDto {
	private String LI_LotNo;
	private String LI_ItemCode;
	private String LI_ItemName;
	private String LI_Item_STND_1;
	private String LI_Item_Material;
	private String LI_Item_Clsfc_1;
	private String LI_Item_Clsfc_2;
	private int LI_Qty;
	private String LI_Create_Date;
	private String LI_Status;
}
