package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {
	
	private String StartDate;
	
	private String EndDate;
	
	private String ItemCode;
	
	private String ClientCode;
	
	private String ItemSendClsfc;
}
