package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {
	
	private String StartDate;
	
	private String EndDate;
	
	private String TargetTime;
	
	private String LotNo;
	
	private String RequestNo;
	
	private String OrderNo;
	
	private String SalesOrderNo;
	
	private String WorkOrderONo;
	
	private String ItemCode;
	
	private String ItemName;
	
	private String MachineCode;
	
	private String MachineName;
		
	private String ClientCode;
	
	private String[] ClientCodeArr;
	
	private String DeptCode;
		
	private String ItemSendClsfc;
	
	private String[] StatusCodeArr;
	
	private int WipStatus;
	
	private String Condition;
	
	private boolean Check;
	
	private String SalesCusNo;
	
	private String Warehouse;
	
	private String Modifier;
	
	private String CrateCode;
	
	private String LotType; 
	
	private String SubType;
	
	private String returnStatus;

}
