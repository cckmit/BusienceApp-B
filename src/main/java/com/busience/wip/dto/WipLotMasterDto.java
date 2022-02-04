package com.busience.wip.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WipLotMasterDto {
	
	private String Wip_Prefix;
	private String Wip_LotNo;
	
	private int Wip_Status;
	private String Wip_DateTime;
	
}
