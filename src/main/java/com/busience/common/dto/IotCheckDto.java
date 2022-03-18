package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotCheckDto {
		
	private String Iot_EquipCode;
	
	private double Iot_Value;
	
	private String Iot_Datetime;
}
