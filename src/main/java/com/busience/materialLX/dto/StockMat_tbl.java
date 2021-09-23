package com.busience.materialLX.dto;

public class StockMat_tbl {
	private int Id;
	private String SM_Code; //재고코드
	private String SM_Name; //재고명
	private String SM_STND_1; //재고규격1
	private int SM_Last_Qty; //전월 재고 수량
	private int SM_In_Qty; //당월 입고 수량
	private int SM_Out_Qty; //당월 출고 수량
	private int SM_Qty; // SM_Output_Qty - SM_Input_Qty 당월 재고량
	private int SM_Total_Qty; // SM_Last_Qty + SM_In_Qty - SM_Out_Qty= 당월 마감 재고량
	private int SM_Prcs_Date; //처리연월
	private int SM_Unit_Price; //재고단가
	private int SM_In_Price; //SM_In_Qty * SM_Unit_Price 당월 입고 총금액	
	private String SM_CLSFC; // 재고구분(분류)
	private String SM_Save_Area; //창고 저장구역
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getSM_Code() {
		return SM_Code;
	}
	public void setSM_Code(String sM_Code) {
		SM_Code = sM_Code;
	}
	public String getSM_Name() {
		return SM_Name;
	}
	public void setSM_Name(String sM_Name) {
		SM_Name = sM_Name;
	}
	public String getSM_STND_1() {
		return SM_STND_1;
	}
	public void setSM_STND_1(String sM_STND_1) {
		SM_STND_1 = sM_STND_1;
	}
	public int getSM_Last_Qty() {
		return SM_Last_Qty;
	}
	public void setSM_Last_Qty(int sM_Last_Qty) {
		SM_Last_Qty = sM_Last_Qty;
	}
	public int getSM_In_Qty() {
		return SM_In_Qty;
	}
	public void setSM_In_Qty(int sM_In_Qty) {
		SM_In_Qty = sM_In_Qty;
	}
	public int getSM_Out_Qty() {
		return SM_Out_Qty;
	}
	public void setSM_Out_Qty(int sM_Out_Qty) {
		SM_Out_Qty = sM_Out_Qty;
	}
	public int getSM_Qty() {
		return SM_Qty;
	}
	public void setSM_Qty(int sM_Qty) {
		SM_Qty = sM_Qty;
	}
	public int getSM_Total_Qty() {
		return SM_Total_Qty;
	}
	public void setSM_Total_Qty(int sM_Total_Qty) {
		SM_Total_Qty = sM_Total_Qty;
	}
	public int getSM_Prcs_Date() {
		return SM_Prcs_Date;
	}
	public void setSM_Prcs_Date(int sM_Prcs_Date) {
		SM_Prcs_Date = sM_Prcs_Date;
	}
	public int getSM_Unit_Price() {
		return SM_Unit_Price;
	}
	public void setSM_Unit_Price(int sM_Unit_Price) {
		SM_Unit_Price = sM_Unit_Price;
	}
	public int getSM_In_Price() {
		return SM_In_Price;
	}
	public void setSM_In_Price(int sM_In_Price) {
		SM_In_Price = sM_In_Price;
	}
	public String getSM_CLSFC() {
		return SM_CLSFC;
	}
	public void setSM_CLSFC(String sM_CLSFC) {
		SM_CLSFC = sM_CLSFC;
	}
	public String getSM_Save_Area() {
		return SM_Save_Area;
	}
	public void setSM_Save_Area(String sM_Save_Area) {
		SM_Save_Area = sM_Save_Area;
	}
}
