package com.busience.production.dto;

import java.util.ArrayList;
import java.util.List;

public class PRODUCTION_MGMT_TBL2 {

	@Override
	public String toString() {
		return "PRODUCTION_MGMT_TBL2 [PRODUCTION_WorkOrder_No=" + PRODUCTION_WorkOrder_No
				+ ", PRODUCTION_WorkOrder_ONo=" + PRODUCTION_WorkOrder_ONo + ", PRODUCTION_Equipment_Code="
				+ PRODUCTION_Equipment_Code + ", PRODUCTION_Volume=" + PRODUCTION_Volume + ", PRODUCTION_Date="
				+ PRODUCTION_Date + ", PRODUCTION_Item_Code=" + PRODUCTION_Item_Code + ", percent=" + percent
				+ ", WorkOrder_StartTime=" + WorkOrder_StartTime + ", ym=" + ym + ", PRODUCTION_REF_CUM_AMT="
				+ PRODUCTION_REF_CUM_AMT + ", PRODUCTION_CALC_CUM_AMT=" + PRODUCTION_CALC_CUM_AMT + ", _children="
				+ _children + "]";
	}

	String PRODUCTION_WorkOrder_No, PRODUCTION_WorkOrder_ONo, PRODUCTION_Equipment_Code, PRODUCTION_Volume,
			PRODUCTION_Date, PRODUCTION_Item_Code;

	String percent, WorkOrder_StartTime, ym;

	int PRODUCTION_REF_CUM_AMT, PRODUCTION_CALC_CUM_AMT;

	List<PRODUCTION_MGMT_TBL2> _children = new ArrayList<PRODUCTION_MGMT_TBL2>();

	public void set_children(PRODUCTION_MGMT_TBL2 data) {
		_children.add(data);
	}

	public List<PRODUCTION_MGMT_TBL2> get_children() {
		return _children;
	}

	public void set_children(List<PRODUCTION_MGMT_TBL2> _children) {
		this._children = _children;
	}

	public int getPRODUCTION_REF_CUM_AMT() {
		return PRODUCTION_REF_CUM_AMT;
	}

	public void setPRODUCTION_REF_CUM_AMT(int pRODUCTION_REF_CUM_AMT) {
		PRODUCTION_REF_CUM_AMT = pRODUCTION_REF_CUM_AMT;
	}

	public int getPRODUCTION_CALC_CUM_AMT() {
		return PRODUCTION_CALC_CUM_AMT;
	}

	public void setPRODUCTION_CALC_CUM_AMT(int pRODUCTION_CALC_CUM_AMT) {
		PRODUCTION_CALC_CUM_AMT = pRODUCTION_CALC_CUM_AMT;
	}

	public PRODUCTION_MGMT_TBL2() {
		super();
	}

	public PRODUCTION_MGMT_TBL2(String ym) {
		super();
		this.ym = ym;
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	public String getWorkOrder_StartTime() {
		return WorkOrder_StartTime;
	}

	public void setWorkOrder_StartTime(String workOrder_StartTime) {
		WorkOrder_StartTime = workOrder_StartTime;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getPRODUCTION_WorkOrder_No() {
		return PRODUCTION_WorkOrder_No;
	}

	public void setPRODUCTION_WorkOrder_No(String pRODUCTION_WorkOrder_No) {
		PRODUCTION_WorkOrder_No = pRODUCTION_WorkOrder_No;
	}

	public String getPRODUCTION_WorkOrder_ONo() {
		return PRODUCTION_WorkOrder_ONo;
	}

	public void setPRODUCTION_WorkOrder_ONo(String pRODUCTION_WorkOrder_ONo) {
		PRODUCTION_WorkOrder_ONo = pRODUCTION_WorkOrder_ONo;
	}

	public String getPRODUCTION_Equipment_Code() {
		return PRODUCTION_Equipment_Code;
	}

	public void setPRODUCTION_Equipment_Code(String pRODUCTION_Equipment_Code) {
		PRODUCTION_Equipment_Code = pRODUCTION_Equipment_Code;
	}

	public String getPRODUCTION_Volume() {
		return PRODUCTION_Volume;
	}

	public void setPRODUCTION_Volume(String pRODUCTION_Volume) {
		PRODUCTION_Volume = pRODUCTION_Volume;
	}

	public String getPRODUCTION_Date() {
		return PRODUCTION_Date;
	}

	public void setPRODUCTION_Date(String pRODUCTION_Date) {
		PRODUCTION_Date = pRODUCTION_Date;
	}

	public String getPRODUCTION_Item_Code() {
		return PRODUCTION_Item_Code;
	}

	public void setPRODUCTION_Item_Code(String pRODUCTION_Item_Code) {
		PRODUCTION_Item_Code = pRODUCTION_Item_Code;
	}

}
