package com.busience.system.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RightsMgmtDto {
	
	private String Rights_User_Type;
	
	private String Rights_Program_Code;
	
	private String Rights_Program_Name;
	
	private boolean Rights_MGMT_Use_Status;
}
