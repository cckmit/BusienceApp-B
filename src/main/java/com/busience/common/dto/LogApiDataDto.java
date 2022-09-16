package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogApiDataDto {
	
	private String CrtfcKey;
	
	private String LogDt;
	
	private String UseSe;
	
	private String SysUser;
	
	private String ConectIp;
	
	private double DataUsgqty;
}
