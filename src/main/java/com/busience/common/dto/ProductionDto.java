package com.busience.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductionDto {

	private String PRODUCTION_WorkOrder_ONo;
	
	private String PRODUCTION_Product_Code;
	
	private String PRODUCTION_Equipment_Code;
	
	private int PRODUCTION_Volume;
	
	private LocalDateTime PRODUCTION_Date;
}
