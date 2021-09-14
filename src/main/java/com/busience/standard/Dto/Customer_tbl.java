package com.busience.standard.Dto;

import java.time.LocalDateTime;

public class Customer_tbl {
	private String Cus_Code; // 거래처코드
	private String Cus_Name; // 거래처이름
	private String Cus_Status; // 상태
	private String Cus_Rprsn; // 대표자명
	private String Cus_Mng; // 담당자명
	private String Cus_Co; // 회사명
	private String Cus_Co_EstYr; // 회사설립연도
	private String Cus_Rprsn_PhNr; // 대표전화번호
	private String Cus_Mng_PhNr; // 담당자전화번호
	private String Cus_Mng_Email; // 담당자이메일
	private String Cus_Adr; // 주소
	private String Cus_Pymn_Date; // 결제일
	private String Cus_Rgstr_Nr; // 사업자등록번호

	// Ŀ���� �÷�
	private Integer Id; // 순번

	// ���� �÷�
	private String Cus_Status_Name;

public String getCus_Code() {
	return Cus_Code;
}

public void setCus_Code(String cus_Code) {
	Cus_Code = cus_Code;
}

public String getCus_Name() {
	return Cus_Name;
}

public void setCus_Name(String cus_Name) {
	Cus_Name = cus_Name;
}

public String getCus_Status() {
	return Cus_Status;
}

public void setCus_Status(String cus_Status) {
	Cus_Status = cus_Status;
}

public String getCus_Rprsn() {
	return Cus_Rprsn;
}

public void setCus_Rprsn(String cus_Rprsn) {
	Cus_Rprsn = cus_Rprsn;
}

public String getCus_Mng() {
	return Cus_Mng;
}

public void setCus_Mng(String cus_Mng) {
	Cus_Mng = cus_Mng;
}

public String getCus_Co() {
	return Cus_Co;
}

public void setCus_Co(String cus_Co) {
	Cus_Co = cus_Co;
}

public String getCus_Co_EstYr() {
	return Cus_Co_EstYr;
}

public void setCus_Co_EstYr(String cus_Co_EstYr) {
	Cus_Co_EstYr = cus_Co_EstYr;
}

public String getCus_Rprsn_PhNr() {
	return Cus_Rprsn_PhNr;
}

public void setCus_Rprsn_PhNr(String cus_Rprsn_PhNr) {
	Cus_Rprsn_PhNr = cus_Rprsn_PhNr;
}

public String getCus_Mng_PhNr() {
	return Cus_Mng_PhNr;
}

public void setCus_Mng_PhNr(String cus_Mng_PhNr) {
	Cus_Mng_PhNr = cus_Mng_PhNr;
}

public String getCus_Mng_Email() {
	return Cus_Mng_Email;
}

public void setCus_Mng_Email(String cus_Mng_Email) {
	Cus_Mng_Email = cus_Mng_Email;
}

public String getCus_Adr() {
	return Cus_Adr;
}

public void setCus_Adr(String cus_Adr) {
	Cus_Adr = cus_Adr;
}

public String getCus_Pymn_Date() {
	return Cus_Pymn_Date;
}

public void setCus_Pymn_Date(String cus_Pymn_Date) {
	Cus_Pymn_Date = cus_Pymn_Date;
}

public String getCus_Rgstr_Nr() {
	return Cus_Rgstr_Nr;
}

public void setCus_Rgstr_Nr(String cus_Rgstr_Nr) {
	Cus_Rgstr_Nr = cus_Rgstr_Nr;
}

public Integer getId() {
	return Id;
}

public void setId(Integer id) {
	Id = id;
}

public String getCus_Status_Name() {
	return Cus_Status_Name;
}

public void setCus_Status_Name(String cus_Status_Name) {
	Cus_Status_Name = cus_Status_Name;
}

@Override
public String toString() {
	return "Customer_tbl [Cus_Code=" + Cus_Code + ", Cus_Name=" + Cus_Name + ", Cus_Status=" + Cus_Status
			+ ", Cus_Rprsn=" + Cus_Rprsn + ", Cus_Mng=" + Cus_Mng + ", Cus_Co=" + Cus_Co + ", Cus_Co_EstYr="
			+ Cus_Co_EstYr + ", Cus_Rprsn_PhNr=" + Cus_Rprsn_PhNr + ", Cus_Mng_PhNr=" + Cus_Mng_PhNr
			+ ", Cus_Mng_Email=" + Cus_Mng_Email + ", Cus_Adr=" + Cus_Adr + ", Cus_Pymn_Date=" + Cus_Pymn_Date
			+ ", Cus_Rgstr_Nr=" + Cus_Rgstr_Nr + ", Id=" + Id + ", Cus_Status_Name=" + Cus_Status_Name + "]";
}
   
}
