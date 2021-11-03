package com.busience.standard.dto;

public class LotMaster_tbl {

	private Integer ID;
	private String LMaster_LotNo;
	private String LMaster_ItemCode;
	private int LMaster_InQty;
	private String LMaster_Cus_No;
	private String LMaster_Cus_Name;
	private String LMaster_Create_Date;

	// �����÷�
	private String InMat_Unit_Price;
	private String InMat_Price;
	private String InMat_Date;
	private String OutMat_Qty;
	private String LMaster_LMQty; // �Ѽ���
	private String LMaster_ItemClsfc_1; // ǰ��з�1
	private String LMaster_ItemName; // ǰ��
	private String LMaster_ItemSTND_1; // �԰�
	private String LMaster_ItemUNIT; // ����

	public String getOutMat_Qty() {
		return OutMat_Qty;
	}

	public void setOutMat_Qty(String outMat_Qty) {
		OutMat_Qty = outMat_Qty;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getLMaster_LotNo() {
		return LMaster_LotNo;
	}

	public void setLMaster_LotNo(String lMaster_LotNo) {
		LMaster_LotNo = lMaster_LotNo;
	}

	public String getLMaster_ItemCode() {
		return LMaster_ItemCode;
	}

	public void setLMaster_ItemCode(String lMaster_ItemCode) {
		LMaster_ItemCode = lMaster_ItemCode;
	}

	public int getLMaster_InQty() {
		return LMaster_InQty;
	}

	public void setLMaster_InQty(int lMaster_InQty) {
		LMaster_InQty = lMaster_InQty;
	}

	public String getLMaster_LMQty() {
		return LMaster_LMQty;
	}

	public void setLMaster_LMQty(String lMaster_LMQty) {
		LMaster_LMQty = lMaster_LMQty;
	}
	
	public String getLMaster_Cus_No() {
		return LMaster_Cus_No;
	}

	public void setLMaster_Cus_No(String lMaster_Cus_No) {
		LMaster_Cus_No = lMaster_Cus_No;
	}

	public String getLMaster_Cus_Name() {
		return LMaster_Cus_Name;
	}

	public void setLMaster_Cus_Name(String lMaster_Cus_Name) {
		LMaster_Cus_Name = lMaster_Cus_Name;
	}

	public String getLMaster_Create_Date() {
		return LMaster_Create_Date;
	}

	public void setLMaster_Create_Date(String lMaster_Create_Date) {
		LMaster_Create_Date = lMaster_Create_Date;
	}

	public String getInMat_Unit_Price() {
		return InMat_Unit_Price;
	}

	public void setInMat_Unit_Price(String inMat_Unit_Price) {
		InMat_Unit_Price = inMat_Unit_Price;
	}

	public String getInMat_Price() {
		return InMat_Price;
	}

	public void setInMat_Price(String inMat_Price) {
		InMat_Price = inMat_Price;
	}

	public String getInMat_Date() {
		return InMat_Date;
	}

	public void setInMat_Date(String inMat_Date) {
		InMat_Date = inMat_Date;
	}

	public String getLMaster_ItemName() {
		return LMaster_ItemName;
	}

	public void setLMaster_ItemName(String lMaster_ItemName) {
		LMaster_ItemName = lMaster_ItemName;
	}

	public String getLMaster_ItemSTND_1() {
		return LMaster_ItemSTND_1;
	}

	public void setLMaster_ItemSTND_1(String lMaster_ItemSTND_1) {
		LMaster_ItemSTND_1 = lMaster_ItemSTND_1;
	}

	public String getLMaster_ItemUNIT() {
		return LMaster_ItemUNIT;
	}

	public void setLMaster_ItemUNIT(String lMaster_ItemUNIT) {
		LMaster_ItemUNIT = lMaster_ItemUNIT;
	}

	public String getLMaster_ItemClsfc_1() {
		return LMaster_ItemClsfc_1;
	}

	public void setLMaster_ItemClsfc_1(String lMaster_ItemClsfc_1) {
		LMaster_ItemClsfc_1 = lMaster_ItemClsfc_1;
	}

}
