package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatInOutDto {
	private String Mat_Code;
	private String Mat_Name;
	private String Mat_OldItemCode;
	private String Mat_STND_1;
	private String Mat_STND_2;
	private String Mat_ITEM_STND_1;
	private String Mat_ITEM_STND_2;
	private int InMat_Qty;
	private int InMat_InReturn_Qty;
	private int InMat_InOther_Qty;
	private int OutMat_Qty;
	private int OutMat_OutReturn_Qty;
	private int OutMat_OutOther_Qty;
	private String Mat_Clsfc;
	private String Mat_Clsfc_Name;
	private String Mat_Date;
}
