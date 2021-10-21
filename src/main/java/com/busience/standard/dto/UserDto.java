package com.busience.standard.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	
	private String USER_CODE;
	
	private String USER_PASSWORD;
	
	private String USER_NAME;
	
	private String USER_TYPE;
	
	private String USER_TYPE_NAME;
	
	private String COMPANY;
	
	private String COMPANY_NAME;
	
	private String DEPT_CODE;
	
	private String DEPT_NAME;
	
	private String USER_MODIFIER;
	
	private LocalDateTime USER_REGDTATE;
	
	private LocalDateTime USER_MODIFY_D;
	
	private String USER_USE_STATUS;

}
