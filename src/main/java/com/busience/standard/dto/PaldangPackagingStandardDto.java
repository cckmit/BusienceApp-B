package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaldangPackagingStandardDto {

	private String packaging_No;
	private String packaging_Clsfc;
	private String packaging_Type;
	private String packaging_Size;
	private String packaging_Item;
	private int packaging_Small;
	private int packaging_Large;
	private String packaging_ModifyDate;
}
