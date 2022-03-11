package com.busience.standard.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	
	private String User_Code;
	
	private String User_Password;
	
	private String User_Name;
	
	private String User_Type;
	
	private String User_Type_Name;
	
	private String Company;
	
	private String Company_Name;
	
	private String Dept_Code;
	
	private String Dept_Name;
	
	private String User_Modifier;
	
	private LocalDateTime User_Regdate;
	
	private LocalDateTime User_Modify_D;
	
	private String User_Use_Status;

}
