package com.busience.material.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestMasterDto {

	private String RM_RequestNo;
	private String RM_UserCode;
	private String RM_UserName;
	private String RM_Date;
	private String RM_Remark;
	private String RM_Check;
	private String RM_Modifier;
	private String RM_ModifyDate;
	
	private String RM_DeptCode;
	private String RM_DeptName;
}
