package com.busience.sales.dto;

public class Sales_StockMat_tbl {
	private int Id;
	private String Sales_SM_Code;
	private int Sales_SM_Last_Qty;
	private int Sales_SM_In_Qty;
	private int Sales_SM_Out_Qty;
	private String Sales_SM_Prcs_Date;

	// add column
	private String Sales_SM_Name;
	private String Sales_SM_STND_1;
	private int Sales_SM_Qty; // SM_Output_Qty - SM_Input_Qty ��� ���
	private int Sales_SM_Total_Qty; // SM_Last_Qty + SM_In_Qty - SM_Out_Qty= ��� ���� ���
	private int Sales_SM_Unit_Price;
	private int Sales_SM_In_Price; // SM_In_Qty * SM_Unit_Price ��� �԰� �ѱݾ�
	private String Sales_SM_CLSFC;
	private int Sales_Output_Order_Qty;
	private String PRODUCT_INFO_STND_1;
	private String Sales_SM_Save_Area;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getSales_SM_Code() {
		return Sales_SM_Code;
	}

	public void setSales_SM_Code(String sales_SM_Code) {
		Sales_SM_Code = sales_SM_Code;
	}

	public int getSales_SM_Last_Qty() {
		return Sales_SM_Last_Qty;
	}

	public void setSales_SM_Last_Qty(int sales_SM_Last_Qty) {
		Sales_SM_Last_Qty = sales_SM_Last_Qty;
	}

	public int getSales_SM_In_Qty() {
		return Sales_SM_In_Qty;
	}

	public void setSales_SM_In_Qty(int sales_SM_In_Qty) {
		Sales_SM_In_Qty = sales_SM_In_Qty;
	}

	public int getSales_SM_Out_Qty() {
		return Sales_SM_Out_Qty;
	}

	public void setSales_SM_Out_Qty(int sales_SM_Out_Qty) {
		Sales_SM_Out_Qty = sales_SM_Out_Qty;
	}

	public String getSales_SM_Prcs_Date() {
		return Sales_SM_Prcs_Date;
	}

	public void setSales_SM_Prcs_Date(String sales_SM_Prcs_Date) {
		Sales_SM_Prcs_Date = sales_SM_Prcs_Date;
	}

	public String getSales_SM_Name() {
		return Sales_SM_Name;
	}

	public void setSales_SM_Name(String sales_SM_Name) {
		Sales_SM_Name = sales_SM_Name;
	}

	public String getSales_SM_STND_1() {
		return Sales_SM_STND_1;
	}

	public void setSales_SM_STND_1(String sales_SM_STND_1) {
		Sales_SM_STND_1 = sales_SM_STND_1;
	}

	public int getSales_SM_Qty() {
		return Sales_SM_Qty;
	}

	public void setSales_SM_Qty(int sales_SM_Qty) {
		Sales_SM_Qty = sales_SM_Qty;
	}

	public int getSales_SM_Total_Qty() {
		return Sales_SM_Total_Qty;
	}

	public void setSales_SM_Total_Qty(int sales_SM_Total_Qty) {
		Sales_SM_Total_Qty = sales_SM_Total_Qty;
	}

	public int getSales_SM_Unit_Price() {
		return Sales_SM_Unit_Price;
	}

	public void setSales_SM_Unit_Price(int sales_SM_Unit_Price) {
		Sales_SM_Unit_Price = sales_SM_Unit_Price;
	}

	public int getSales_SM_In_Price() {
		return Sales_SM_In_Price;
	}

	public void setSales_SM_In_Price(int sales_SM_In_Price) {
		Sales_SM_In_Price = sales_SM_In_Price;
	}

	public String getSales_SM_CLSFC() {
		return Sales_SM_CLSFC;
	}

	public void setSales_SM_CLSFC(String sales_SM_CLSFC) {
		Sales_SM_CLSFC = sales_SM_CLSFC;
	}

	public int getSales_Output_Order_Qty() {
		return Sales_Output_Order_Qty;
	}

	public void setSales_Output_Order_Qty(int sales_Output_Order_Qty) {
		Sales_Output_Order_Qty = sales_Output_Order_Qty;
	}

	public String getPRODUCT_INFO_STND_1() {
		return PRODUCT_INFO_STND_1;
	}

	public void setPRODUCT_INFO_STND_1(String pRODUCT_INFO_STND_1) {
		PRODUCT_INFO_STND_1 = pRODUCT_INFO_STND_1;
	}

	public String getSales_SM_Save_Area() {
		return Sales_SM_Save_Area;
	}

	public void setSales_SM_Save_Area(String sales_SM_Save_Area) {
		Sales_SM_Save_Area = sales_SM_Save_Area;
	}
}
