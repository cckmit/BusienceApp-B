package com.busience.standard.Dto;

public class BOM_tbl {
	private Integer BOM_ID;
	private String BOM_Parent_ItemCode;
	private String BOM_ItemCode;
	private String BOM_ItemName;
	private String BOM_STND_1;
	private float BOM_Qty;
	private String BOM_Unit_Name;
	private String BOM_State;
	private String BOM_ChildExist; 
	private String BOM_Modifier; 
	private String BOM_Modify_Date;
	public Integer getBOM_ID() {
		return BOM_ID;
	}
	public void setBOM_ID(Integer bOM_ID) {
		BOM_ID = bOM_ID;
	}
	public String getBOM_Parent_ItemCode() {
		return BOM_Parent_ItemCode;
	}
	public void setBOM_Parent_ItemCode(String bOM_Parent_ItemCode) {
		BOM_Parent_ItemCode = bOM_Parent_ItemCode;
	}
	public String getBOM_ItemCode() {
		return BOM_ItemCode;
	}
	public void setBOM_ItemCode(String bOM_ItemCode) {
		BOM_ItemCode = bOM_ItemCode;
	}
	public String getBOM_ItemName() {
		return BOM_ItemName;
	}
	public void setBOM_ItemName(String bOM_ItemName) {
		BOM_ItemName = bOM_ItemName;
	}
	public String getBOM_STND_1() {
		return BOM_STND_1;
	}
	public void setBOM_STND_1(String bOM_STND_1) {
		BOM_STND_1 = bOM_STND_1;
	}
	public float getBOM_Qty() {
		return BOM_Qty;
	}
	public void setBOM_Qty(float bOM_Qty) {
		BOM_Qty = bOM_Qty;
	}
	public String getBOM_Unit_Name() {
		return BOM_Unit_Name;
	}
	public void setBOM_Unit_Name(String bOM_Unit_Name) {
		BOM_Unit_Name = bOM_Unit_Name;
	}
	public String getBOM_State() {
		return BOM_State;
	}
	public void setBOM_State(String bOM_State) {
		BOM_State = bOM_State;
	}
	public String getBOM_ChildExist() {
		return BOM_ChildExist;
	}
	public void setBOM_ChildExist(String bOM_ChildExist) {
		BOM_ChildExist = bOM_ChildExist;
	}
	public String getBOM_Modifier() {
		return BOM_Modifier;
	}
	public void setBOM_Modifier(String bOM_Modifier) {
		BOM_Modifier = bOM_Modifier;
	}
	public String getBOM_Modify_Date() {
		return BOM_Modify_Date;
	}
	public void setBOM_Modify_Date(String bOM_Modify_Date) {
		BOM_Modify_Date = bOM_Modify_Date;
	}
	@Override
	public String toString() {
		return "BOM_tbl [BOM_ID=" + BOM_ID + ", BOM_Parent_ItemCode=" + BOM_Parent_ItemCode + ", BOM_ItemCode="
				+ BOM_ItemCode + ", BOM_ItemName=" + BOM_ItemName + ", BOM_STND_1=" + BOM_STND_1 + ", BOM_Qty="
				+ BOM_Qty + ", BOM_Unit_Name=" + BOM_Unit_Name + ", BOM_State="
				+ BOM_State + ", BOM_ChildExist=" + BOM_ChildExist + ", BOM_Modifier=" + BOM_Modifier
				+ ", BOM_Modify_Date=" + BOM_Modify_Date + "]";
	}
}
