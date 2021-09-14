package com.busience.standard.Dto;


public class Spare_Type_tbl {
	int id; // 순번
	private String Component_Code; // 부품코드
	private String Component_Name; // 부품명
	private String Component_Standard; // 규격
	private String Component_Producer; // 제작사
	private String Component_Model; // 모델
	private String Component_MachineCode; // 설비코드
	private String Component_MachineName; // 설비명
	private String Component_Cus_Code; // 구입처
	private String Component_Cus_Name; // 구입처명
	private int Component_Latest_Unit_Price; // 최신구입단가
	private String Component_Use_Date; // 사용기한
	private String Component_Use_Status; // 사용유무
	private String Component_Latest_InMat_Date; // 최근입고일자
	private String Component_Latest_OutMat_Date; // 최근출고일자
	private String Component_Picture; // 사진
	private String Component_Info_Remark; // 비고

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponent_Code() {
		return Component_Code;
	}

	public void setComponent_Code(String component_Code) {
		Component_Code = component_Code;
	}

	public String getComponent_Name() {
		return Component_Name;
	}

	public void setComponent_Name(String component_Name) {
		Component_Name = component_Name;
	}

	public String getComponent_Standard() {
		return Component_Standard;
	}

	public void setComponent_Standard(String component_Standard) {
		Component_Standard = component_Standard;
	}

	public String getComponent_Producer() {
		return Component_Producer;
	}

	public void setComponent_Producer(String component_Producer) {
		Component_Producer = component_Producer;
	}

	public String getComponent_Model() {
		return Component_Model;
	}

	public void setComponent_Model(String component_Model) {
		Component_Model = component_Model;
	}

	public String getComponent_Cus_Code() {
		return Component_Cus_Code;
	}

	public void setComponent_Cus_Code(String component_Cus_Code) {
		Component_Cus_Code = component_Cus_Code;
	}

	public String getComponent_Cus_Name() {
		return Component_Cus_Name;
	}

	public void setComponent_Cus_Name(String component_Cus_Name) {
		Component_Cus_Name = component_Cus_Name;
	}

	public int getComponent_Latest_Unit_Price() {
		return Component_Latest_Unit_Price;
	}

	public void setComponent_Latest_Unit_Price(int component_Latest_Unit_Price) {
		Component_Latest_Unit_Price = component_Latest_Unit_Price;
	}

	public String getComponent_Use_Date() {
		return Component_Use_Date;
	}

	public void setComponent_Use_Date(String component_Use_Date) {
		Component_Use_Date = component_Use_Date;
	}

	public String getComponent_Use_Status() {
		return Component_Use_Status;
	}

	public void setComponent_Use_Status(String component_Use_Status) {
		Component_Use_Status = component_Use_Status;
	}

	public String getComponent_Latest_InMat_Date() {
		return Component_Latest_InMat_Date;
	}

	public void setComponent_Latest_InMat_Date(String component_Latest_InMat_Date) {
		Component_Latest_InMat_Date = component_Latest_InMat_Date;
	}

	public String getComponent_Latest_OutMat_Date() {
		return Component_Latest_OutMat_Date;
	}

	public void setComponent_Latest_OutMat_Date(String component_Latest_OutMat_Date) {
		Component_Latest_OutMat_Date = component_Latest_OutMat_Date;
	}

	public String getComponent_Picture() {
		return Component_Picture;
	}

	public void setComponent_Picture(String component_Picture) {
		Component_Picture = component_Picture;
	}

	public String getComponent_Info_Remark() {
		return Component_Info_Remark;
	}

	public void setComponent_Info_Remark(String component_Info_Remark) {
		Component_Info_Remark = component_Info_Remark;
	}

	public String getComponent_MachineCode() {
		return Component_MachineCode;
	}

	public void setComponent_MachineCode(String component_MachineCode) {
		Component_MachineCode = component_MachineCode;
	}

	public String getComponent_MachineName() {
		return Component_MachineName;
	}

	public void setComponent_MachineName(String component_MachineName) {
		Component_MachineName = component_MachineName;
	}

	@Override
	public String toString() {
		return "Spare_Type_tbl [id=" + id + ", Component_Code=" + Component_Code + ", Component_Name=" + Component_Name
				+ ", Component_Standard=" + Component_Standard + ", Component_Producer=" + Component_Producer
				+ ", Component_Model=" + Component_Model + ", Component_Cus_Code=" + Component_Cus_Code
				+ ", Component_Cus_Name=" + Component_Cus_Name + ", Component_Latest_Unit_Price="
				+ Component_Latest_Unit_Price + ", Component_Use_Date=" + Component_Use_Date + ", Component_Use_Status="
				+ Component_Use_Status + ", Component_Latest_InMat_Date=" + Component_Latest_InMat_Date
				+ ", Component_Latest_OutMat_Date=" + Component_Latest_OutMat_Date + ", Component_Picture="
				+ Component_Picture + ", Component_Info_Remark=" + Component_Info_Remark + "]";
	}

}
