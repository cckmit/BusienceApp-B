package com.busience.salesLX.dto;

public class Sales_LotMaster_tbl {
	private Integer ID;
	private String Sales_LMaster_LotNo;
	private String Sales_LMaster_ItemCode;
	private String Sales_LMaster_ItemName;
	private int Sales_LMaster_InQty;
	private String Sales_LMaster_Create_Date;

	// join column
	private String Sales_LMaster_ItemClsfc_1;
	private String Sales_LMaster_ItemSTND_1;
	private String Sales_LMaster_ItemUNIT;
	private Integer Sales_LMaster_SLMQty;
	private String Sales_LMaster_WareHouse;
	private String Sales_LMaster_Save_Area;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getSales_LMaster_LotNo() {
		return Sales_LMaster_LotNo;
	}

	public void setSales_LMaster_LotNo(String sales_LMaster_LotNo) {
		Sales_LMaster_LotNo = sales_LMaster_LotNo;
	}

	public String getSales_LMaster_ItemCode() {
		return Sales_LMaster_ItemCode;
	}

	public void setSales_LMaster_ItemCode(String sales_LMaster_ItemCode) {
		Sales_LMaster_ItemCode = sales_LMaster_ItemCode;
	}

	public String getSales_LMaster_ItemName() {
		return Sales_LMaster_ItemName;
	}

	public void setSales_LMaster_ItemName(String sales_LMaster_ItemName) {
		Sales_LMaster_ItemName = sales_LMaster_ItemName;
	}

	public int getSales_LMaster_InQty() {
		return Sales_LMaster_InQty;
	}

	public void setSales_LMaster_InQty(int sales_LMaster_InQty) {
		Sales_LMaster_InQty = sales_LMaster_InQty;
	}

	public String getSales_LMaster_Create_Date() {
		return Sales_LMaster_Create_Date;
	}

	public void setSales_LMaster_Create_Date(String sales_LMaster_Create_Date) {
		Sales_LMaster_Create_Date = sales_LMaster_Create_Date;
	}

	public String getSales_LMaster_ItemClsfc_1() {
		return Sales_LMaster_ItemClsfc_1;
	}

	public void setSales_LMaster_ItemClsfc_1(String sales_LMaster_ItemClsfc_1) {
		Sales_LMaster_ItemClsfc_1 = sales_LMaster_ItemClsfc_1;
	}

	public String getSales_LMaster_ItemSTND_1() {
		return Sales_LMaster_ItemSTND_1;
	}

	public void setSales_LMaster_ItemSTND_1(String sales_LMaster_ItemSTND_1) {
		Sales_LMaster_ItemSTND_1 = sales_LMaster_ItemSTND_1;
	}

	public String getSales_LMaster_ItemUNIT() {
		return Sales_LMaster_ItemUNIT;
	}

	public void setSales_LMaster_ItemUNIT(String sales_LMaster_ItemUNIT) {
		Sales_LMaster_ItemUNIT = sales_LMaster_ItemUNIT;
	}

	public Integer getSales_LMaster_SLMQty() {
		return Sales_LMaster_SLMQty;
	}

	public void setSales_LMaster_SLMQty(Integer sales_LMaster_SLMQty) {
		Sales_LMaster_SLMQty = sales_LMaster_SLMQty;
	}

	public String getSales_LMaster_WareHouse() {
		return Sales_LMaster_WareHouse;
	}

	public void setSales_LMaster_WareHouse(String sales_LMaster_WareHouse) {
		Sales_LMaster_WareHouse = sales_LMaster_WareHouse;
	}

	public String getSales_LMaster_Save_Area() {
		return Sales_LMaster_Save_Area;
	}

	public void setSales_LMaster_Save_Area(String sales_LMaster_Save_Area) {
		Sales_LMaster_Save_Area = sales_LMaster_Save_Area;
	}
}
