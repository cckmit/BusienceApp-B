package com.busience.wip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WipLotTransDto {
	
	private String Wip_LotNo;
	
	private String Wip_Process;
	private String Wip_Process_Name;
	private String Wip_Process_No;
	
	private String Wip_InputDate;
	private String Wip_OutputDate;
	
	private String wip_SaveTime;
}
