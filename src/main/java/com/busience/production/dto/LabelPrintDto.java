package com.busience.production.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabelPrintDto {

	private String Small_Packaging_LotNo;
	private String Production_LotNo;
	private String LotNo;
	
	private String MachineCode;
	private String MachineName;
	
	private String ItemCode;
	private String ItemName;
	private String ItemSTND1;
	private String ItemSTND2;
	private String ItemMaterial;
	private String ItemMaterial_Name;
	private String ItemClsfc1;
	private String ItemClsfc1_Name;
	private String ItemClsfc2;
	private String ItemClsfc2_Name;
	private double Qty;

	private String ClientCode;
	private String ClientName;
}
