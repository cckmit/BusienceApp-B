package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDto {
	private String Cus_Code; // 거래처코드
	private String Cus_Name; // 거래처이름
	private String Cus_Clsfc; // 분류
	private String Cus_Clsfc_Name; // 분류명
	private String Cus_Status; // 납품조건
	private String Cus_Status_Name; //납품조건명
	private String Cus_Pymn_Date; // 결제일
	private String Cus_Pymn_Date_Name;
	private String Cus_Co; // 회사명
	private String Cus_Adr; // 주소
	private String Cus_Rgstr_Nr; // 사업자등록번호
	private String Cus_Co_EstYr; // 회사설립연도
	private String Cus_Rprsn; // 대표자명
	private String Cus_Rprsn_PhNr; // 대표전화번호
	private String Cus_Rprsn_Email; // 대표자이메일
	private String Cus_Mng; // 담당자명
	private String Cus_Mng_PhNr; // 담당자전화번호
	private String Cus_Mng_Email; // 담당자이메일
}
