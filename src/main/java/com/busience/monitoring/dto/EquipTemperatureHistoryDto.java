package com.busience.monitoring.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EquipTemperatureHistoryDto {
	private int Temp_Monthly;
	private int Temp_Daily;
	private int Temp_Hourly;
	
	private float Temp_Value;
	private String Temp_Time;
	private String Temp_EquipCode;
	private String Temp_EquipName;
	private String Temp_No;
}
