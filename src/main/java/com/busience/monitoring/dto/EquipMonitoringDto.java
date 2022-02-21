package com.busience.monitoring.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EquipMonitoringDto {
	private String Equip_Code;
	private String Equip_Time;
	private float Humi; 
	private float Speed; 
	private float Temp; 
	private boolean Equip_Status; 
	private String Equip_No;
	
	private String StartTime;
	private String EndTime;
	private String UpperTime;
	private String UnderTime;
}
