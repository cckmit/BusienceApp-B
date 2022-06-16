package com.busience.tablet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RawMaterialDto {
	
	private String Production_LotNo;
	private String Material_LotNo;
	private String Material_ItemCode;
	private String Material_ItemName;
	private String Qty;
	private String CrateDate;
}
