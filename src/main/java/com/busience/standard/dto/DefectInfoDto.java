package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefectInfoDto {
	private String Defect_Code;
	private String Defect_Name;
	private String Defect_Abr;
	private String Defect_Rmrks;
	private String Defect_Modify_D;
	private String Defect_Modifier;
	private String Defect_Use_Status;
}
