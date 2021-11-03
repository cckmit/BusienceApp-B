package com.busience.system.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserMenuDto {

	private String User_Code;
	private String Program_Code;
	
	private String Menu_Code;
	private String Menu_Parent_No;
	private String Menu_Parent_Name;
	private String Menu_Child_No;
	private String Menu_Name;
	private String Menu_PageName;
}
