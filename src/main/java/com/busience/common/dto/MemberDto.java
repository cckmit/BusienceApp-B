package com.busience.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	
	private String USER_CODE;
	
	private String USER_PASSWORD;
	
	private String USER_NAME;
	
	private String USER_TYPE;
	
	private String COMPANY;
	
	private String DEPT_CODE;
	
	private String USER_MODIFIER;
	
	private LocalDateTime USER_REGDTATE;
	
	private LocalDateTime USER_MODIFY_D;
	
	private String USER_USE_STATUS;

}
