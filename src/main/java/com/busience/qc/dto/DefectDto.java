package com.busience.qc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectDto {
	private String Defect_ONo;
	
	private String Defect_ItemCode;
	
	private String Defect_Code;
	
	private String Defect_Name;
	
	private int Defect_Qty;
	
	private String Defect_Insert_Time;
}
