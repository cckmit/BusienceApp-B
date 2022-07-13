package com.busience.monitoring.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WrappingStatusMonitoringDto {
	private String Wrap_ItemCode;
	private String Wrap_MachineCode;	
	private String Wrap_Desc;
	private String Wrap_Measure1;
	private String Wrap_Measure2;
	private String Wrap_Material;
	private String Wrap_Type1;
	private String Wrap_Type2;
	private Integer Wrap_RemainQty;
	private Integer Wrap_Qty;	
}
