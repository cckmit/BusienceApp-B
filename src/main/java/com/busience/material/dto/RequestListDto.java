package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestListDto {

	private int Request_lNo;
	private String Request_lReqNo;
	private String Request_lCode;
	private int Request_lQty;
	private int Request_lSum;
	private int Request_lNot_Stocked;
	private String Request_Send_Clsfc;
	private String Request_lInfo_Remark;

	// add column
	private String Request_lName;
	private int Request_SM_Last_Qty; // Stock
}
