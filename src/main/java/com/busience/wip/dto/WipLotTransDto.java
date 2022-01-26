package com.busience.wip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WipLotTransDto {
	
	private String Wip_Prefix;
	private String Wip_LotNo;
	
	private String Wip_Process_Type;
	private String Wip_Process_Name;
	private int Wip_Process_No;
	
	private String Wip_InputDate;
	private String Wip_OutputDate;	
	private String Wip_SaveTime;
	
	private String Wip_InputDate_P1;
	private String Wip_OutputDate_P1;
	private String Wip_SaveDate_P1;
	
	private String Wip_InputDate_P2;
	private String Wip_OutputDate_P2;
	private String Wip_SaveDate_P2;
	
	private String Wip_InputDate_P3;
	private String Wip_OutputDate_P3;
	private String Wip_SaveDate_P3;
	
	private String Wip_InputDate_P4;
	private String Wip_OutputDate_P4;
	private String Wip_SaveDate_P4;
	
	private String Wip_InputDate_P5;
	private String Wip_OutputDate_P5;
	private String Wip_SaveDate_P5;
}
