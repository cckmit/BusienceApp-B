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
	private String Material_Item_Stnd_1; // 이력조회
	private String Material_Item_Clsfc_1; // 이력조회
	private String Material_Item_Clsfc_2; // 이력조회
	private String Material_Item_Material; // 이력조회
	private String CreateDate;
}
