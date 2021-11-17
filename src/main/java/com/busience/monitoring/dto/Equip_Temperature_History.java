package com.busience.monitoring.dto;

public class Equip_Temperature_History {
	String Temp_ONo,Temp_Value,Temp_Time;

	public String getTemp_ONo() {
		return Temp_ONo;
	}

	public void setTemp_ONo(String temp_ONo) {
		Temp_ONo = temp_ONo;
	}

	public String getTemp_Value() {
		return Temp_Value;
	}

	public void setTemp_Value(String temp_Value) {
		Temp_Value = temp_Value;
	}

	public String getTemp_Time() {
		return Temp_Time;
	}

	public void setTemp_Time(String temp_Time) {
		Temp_Time = temp_Time;
	}

	@Override
	public String toString() {
		return "Equip_Temperature_History [Temp_ONo=" + Temp_ONo + ", Temp_Value=" + Temp_Value + ", Temp_Time="
				+ Temp_Time + "]";
	}
}
