package com.busience.tablet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrateDto {

	private String C_CrateCode;
	private String C_Condition;
	private String C_Create_Date;
	private boolean C_Use_Status;
	private String C_Production_LotNo;
}

