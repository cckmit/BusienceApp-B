package com.busience.production.dto;

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
	
	private int PRODUCTION_Qty;
	
	private String PRODUCTION_Info_STND_1;
	
	private String PRODUCTION_Item_CLSFC_NAME_1;
	
	private String PRODUCTION_Item_CLSFC_NAME_2;
	
	private String PRODUCTION_Date;
	
	private String PRODUCTION_Start_Date;
	
	private String PRODUCTION_Total_Work_Time;
}
