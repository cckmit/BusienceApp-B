package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestMasterDto {

	private String Request_mReqNo;
	private String Request_mUser;
	private String Request_mDate;
	private String Request_mRemarks;
	private String Request_mCheck;
	private String Request_mModifier;
	private String Request_mModify_Date;
	
	private String DEPT_CODE;
	private String DEPT_NAME;
	private String User_Name;
}
