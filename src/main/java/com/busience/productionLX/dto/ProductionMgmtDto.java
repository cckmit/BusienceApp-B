package com.busience.productionLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductionMgmtDto {
	
	private int PRODUCTION_WorkOrder_No;
	
	private String PRODUCTION_WorkOrder_ONo;
	
	private String PRODUCTION_Product_Code;
	
	private String PRODUCTION_Product_Name;
	
	private String PRODUCTION_Equipment_Code;
	
	private String PRODUCTION_Equipment_Name;
	
	private int PRODUCTION_Volume;
	
	private String PRODUCTION_Date;
}
