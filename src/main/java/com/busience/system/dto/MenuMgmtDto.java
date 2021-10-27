package com.busience.system.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuMgmtDto {
	private String Menu_User_Code;
	private String Menu_Program_Code;
	private String Menu_Program_Name;
	private boolean Menu_Read_Use_Status;
	private boolean Menu_Write_Use_Status;
	private boolean Menu_Delete_Use_Status;
	private boolean Menu_MGMT_Use_Status;
}
