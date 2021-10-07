package com.busience.system.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu_tbl {
	private String Menu_Code;
	private String Menu_Parent_No; 
	private String Menu_Child_No; 
	private String Menu_Name; 
	private String Menu_PageName;
	private boolean Menu_Use_Status;
}
