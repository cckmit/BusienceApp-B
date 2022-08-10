package com.busience.qc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IqcDto {
	private String ItemCode;
	
	private String ItemName;
	
	private String Stnd_1;
	
	private String Stnd_2;
	
	private String Mtrl_Clsfc;
	
	private String Mtrl_Clsfc_Name;
	
	private String Clsfc_1;
	
	private String Clsfc_1_Name;
	
	private String Clsfc_2;
	
	private String Clsfc_2_Name;
	
	private double Pass_Qty;
	
	private double Fail_Qty;

	private double Total_Qty;
	
	private double Pass_Rate;
}
