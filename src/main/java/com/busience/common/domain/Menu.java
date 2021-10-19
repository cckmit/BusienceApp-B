package com.busience.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Menu {

	private String Menu_Code;
	
	private String Menu_Parent_No;
	
	private String Menu_Child_No;
	
	private String Menu_Name;
	
	private String Menu_PageName;
	
	private boolean Menu_Use_Status;
}
