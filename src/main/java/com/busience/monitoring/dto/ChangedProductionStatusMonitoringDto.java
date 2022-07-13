package com.busience.monitoring.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangedProductionStatusMonitoringDto {
	private String Prod_ItemCode;
	private String Prod_MachineCode;	
	private Integer Right_Prod_MachineCode;
	private String Prod_Desc;
	private String Prod_Measure1;
	private String Prod_Material;
	private String Prod_Type1;
	private String Prod_Type2;
	private Integer Prod_Qty;	
}
