package com.busience.standard.dto;

public class Packaging_Standard_tbl {
	private Integer ID;
	private String Packaging_Cus_Status;
	private String Packaging_Cus_Status_Name;
	private String Packaging_ItemCode;
	private String Packaging_ItemName;
	private String Packaging_Cus_Code;
	private String Packaging_Cus_Name;
	private String Packaging_Label_Type;
	private String Packaging_Classified_LOT;
	private int Packaging_Small_Unit;
	private int Packaging_Min_Order_Qty;
	private int Packaging_Big_Box;
	private int Packaging_Rate;
	private boolean Packaging_Use_Status;
	private String packaging_Modify_Date;
	private String packaging_Modifier;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getPackaging_Cus_Status() {
		return Packaging_Cus_Status;
	}
	public void setPackaging_Cus_Status(String packaging_Cus_Status) {
		Packaging_Cus_Status = packaging_Cus_Status;
	}
	public String getPackaging_Cus_Status_Name() {
		return Packaging_Cus_Status_Name;
	}
	public void setPackaging_Cus_Status_Name(String packaging_Cus_Status_Name) {
		Packaging_Cus_Status_Name = packaging_Cus_Status_Name;
	}
	public String getPackaging_ItemCode() {
		return Packaging_ItemCode;
	}
	public void setPackaging_ItemCode(String packaging_ItemCode) {
		Packaging_ItemCode = packaging_ItemCode;
	}
	public String getPackaging_ItemName() {
		return Packaging_ItemName;
	}
	public void setPackaging_ItemName(String packaging_ItemName) {
		Packaging_ItemName = packaging_ItemName;
	}
	public String getPackaging_Cus_Code() {
		return Packaging_Cus_Code;
	}
	public void setPackaging_Cus_Code(String packaging_Cus_Code) {
		Packaging_Cus_Code = packaging_Cus_Code;
	}
	public String getPackaging_Cus_Name() {
		return Packaging_Cus_Name;
	}
	public void setPackaging_Cus_Name(String packaging_Cus_Name) {
		Packaging_Cus_Name = packaging_Cus_Name;
	}
	public String getPackaging_Label_Type() {
		return Packaging_Label_Type;
	}
	public void setPackaging_Label_Type(String packaging_Label_Type) {
		Packaging_Label_Type = packaging_Label_Type;
	}
	public String getPackaging_Classified_LOT() {
		return Packaging_Classified_LOT;
	}
	public void setPackaging_Classified_LOT(String packaging_Classified_LOT) {
		Packaging_Classified_LOT = packaging_Classified_LOT;
	}
	public int getPackaging_Small_Unit() {
		return Packaging_Small_Unit;
	}
	public void setPackaging_Small_Unit(int packaging_Small_Unit) {
		Packaging_Small_Unit = packaging_Small_Unit;
	}
	public int getPackaging_Min_Order_Qty() {
		return Packaging_Min_Order_Qty;
	}
	public void setPackaging_Min_Order_Qty(int packaging_Min_Order_Qty) {
		Packaging_Min_Order_Qty = packaging_Min_Order_Qty;
	}
	public int getPackaging_Big_Box() {
		return Packaging_Big_Box;
	}
	public void setPackaging_Big_Box(int packaging_Big_Box) {
		Packaging_Big_Box = packaging_Big_Box;
	}
	public int getPackaging_Rate() {
		return Packaging_Rate;
	}
	public void setPackaging_Rate(int packaging_Rate) {
		Packaging_Rate = packaging_Rate;
	}
	public boolean isPackaging_Use_Status() {
		return Packaging_Use_Status;
	}
	public void setPackaging_Use_Status(boolean packaging_Use_Status) {
		Packaging_Use_Status = packaging_Use_Status;
	}
	public String getPackaging_Modify_Date() {
		return packaging_Modify_Date;
	}
	public void setPackaging_Modify_Date(String packaging_Modify_Date) {
		this.packaging_Modify_Date = packaging_Modify_Date;
	}
	public String getPackaging_Modifier() {
		return packaging_Modifier;
	}
	public void setPackaging_Modifier(String packaging_Modifier) {
		this.packaging_Modifier = packaging_Modifier;
	}
	@Override
	public String toString() {
		return "Packaging_Standard_tbl [ID=" + ID + ", Packaging_Cus_Status=" + Packaging_Cus_Status
				+ ", Packaging_Cus_Status_Name=" + Packaging_Cus_Status_Name + ", Packaging_ItemCode="
				+ Packaging_ItemCode + ", Packaging_ItemName=" + Packaging_ItemName + ", Packaging_Cus_Code="
				+ Packaging_Cus_Code + ", Packaging_Cus_Name=" + Packaging_Cus_Name + ", Packaging_Label_Type="
				+ Packaging_Label_Type + ", Packaging_Classified_LOT=" + Packaging_Classified_LOT
				+ ", Packaging_Small_Unit=" + Packaging_Small_Unit + ", Packaging_Min_Order_Qty="
				+ Packaging_Min_Order_Qty + ", Packaging_Big_Box=" + Packaging_Big_Box + ", Packaging_Rate="
				+ Packaging_Rate + ", Packaging_Use_Status=" + Packaging_Use_Status + ", packaging_Modify_Date="
				+ packaging_Modify_Date + ", packaging_Modifier=" + packaging_Modifier + "]";
	}
}
