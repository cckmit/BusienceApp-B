package com.busience.productionLX.dto;

public class PRODUCTION_INFO_TBL {

	private int Id;
	private String PRODUCTION_SERIAL_NUM; // 시리얼 넘버
	private String PRODUCTION_INFO_NUM; // 순번
	private String PRODUCTION_EQUIPMENT_CODE; // 설비코드
	private String PRODUCTION_EQUIPMENT_INFO_NAME; // 설비명
	private int PRODUCTION_P_Qty; // 생산수량
	private String PRODUCTION_DEFECT_CODE; // 불량정보No
	private String PRODUCTION_DEFECT_NAME; // 불량명
	private int PRODUCTION_B_Qty; // 불량수량
	private int PRODUCTION_PRODUCTS_VOLUME; // 양품수량
	private String PRODUCTION_DEFECT_RATE; // 불량율
	private int PRODUCTION_DEFECT_CODE_SUM; // 불량합계
	private String PRODUCTION_PRODUCT_CODE; // 제품No
	private String PRODUCT_ITEM_NAME; // 제품명
	private String PRODUCTION_MOLD_INFO_CODE;// 금형코드
	private String PRODUCTION_MOLD_INFO_NAME;// 금형명
	private String PRODUCTION_USER_NAME; // 사용자명
	private String PRODUCTION_USER_CODE; // 사용자코드
	private String PRODUCTION_MODIFY_D; // 날짜
	private String PRODUCTION_CC; // 구분
	private String PRODUCT_INFO_STND_1; // 규격

	private String TIME; // 이유 : 생산,불량현황 모니터링의 시간데이터를 받는 역할 / 이름 : 박성민

	private Integer PRODUCTION_WorkOrder_No;
	private String PRODUCTION_WorkOrder_ONo;
	private String PRODUCTION_Date;

	public String getPRODUCT_INFO_STND_1() {
		return PRODUCT_INFO_STND_1;
	}

	public void setPRODUCT_INFO_STND_1(String pRODUCT_INFO_STND_1) {
		PRODUCT_INFO_STND_1 = pRODUCT_INFO_STND_1;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPRODUCTION_SERIAL_NUM() {
		return PRODUCTION_SERIAL_NUM;
	}

	public void setPRODUCTION_SERIAL_NUM(String pRODUCTION_SERIAL_NUM) {
		PRODUCTION_SERIAL_NUM = pRODUCTION_SERIAL_NUM;
	}

	public String getPRODUCTION_INFO_NUM() {
		return PRODUCTION_INFO_NUM;
	}

	public void setPRODUCTION_INFO_NUM(String pRODUCTION_INFO_NUM) {
		PRODUCTION_INFO_NUM = pRODUCTION_INFO_NUM;
	}

	public String getPRODUCTION_EQUIPMENT_CODE() {
		return PRODUCTION_EQUIPMENT_CODE;
	}

	public void setPRODUCTION_EQUIPMENT_CODE(String pRODUCTION_EQUIPMENT_CODE) {
		PRODUCTION_EQUIPMENT_CODE = pRODUCTION_EQUIPMENT_CODE;
	}

	public String getPRODUCTION_EQUIPMENT_INFO_NAME() {
		return PRODUCTION_EQUIPMENT_INFO_NAME;
	}

	public void setPRODUCTION_EQUIPMENT_INFO_NAME(String pRODUCTION_EQUIPMENT_INFO_NAME) {
		PRODUCTION_EQUIPMENT_INFO_NAME = pRODUCTION_EQUIPMENT_INFO_NAME;
	}

	public int getPRODUCTION_P_Qty() {
		return PRODUCTION_P_Qty;
	}

	public void setPRODUCTION_P_Qty(int pRODUCTION_P_Qty) {
		PRODUCTION_P_Qty = pRODUCTION_P_Qty;
	}

	public String getPRODUCTION_DEFECT_CODE() {
		return PRODUCTION_DEFECT_CODE;
	}

	public void setPRODUCTION_DEFECT_CODE(String pRODUCTION_DEFECT_CODE) {
		PRODUCTION_DEFECT_CODE = pRODUCTION_DEFECT_CODE;
	}

	public String getPRODUCTION_DEFECT_NAME() {
		return PRODUCTION_DEFECT_NAME;
	}

	public void setPRODUCTION_DEFECT_NAME(String pRODUCTION_DEFECT_NAME) {
		PRODUCTION_DEFECT_NAME = pRODUCTION_DEFECT_NAME;
	}

	public int getPRODUCTION_B_Qty() {
		return PRODUCTION_B_Qty;
	}

	public void setPRODUCTION_B_Qty(int pRODUCTION_B_Qty) {
		PRODUCTION_B_Qty = pRODUCTION_B_Qty;
	}

	public int getPRODUCTION_PRODUCTS_VOLUME() {
		return PRODUCTION_PRODUCTS_VOLUME;
	}

	public void setPRODUCTION_PRODUCTS_VOLUME(int pRODUCTION_PRODUCTS_VOLUME) {
		PRODUCTION_PRODUCTS_VOLUME = pRODUCTION_PRODUCTS_VOLUME;
	}

	public String getPRODUCTION_DEFECT_RATE() {
		return PRODUCTION_DEFECT_RATE;
	}

	public void setPRODUCTION_DEFECT_RATE(String pRODUCTION_DEFECT_RATE) {
		PRODUCTION_DEFECT_RATE = pRODUCTION_DEFECT_RATE;
	}

	public int getPRODUCTION_DEFECT_CODE_SUM() {
		return PRODUCTION_DEFECT_CODE_SUM;
	}

	public void setPRODUCTION_DEFECT_CODE_SUM(int pRODUCTION_DEFECT_CODE_SUM) {
		PRODUCTION_DEFECT_CODE_SUM = pRODUCTION_DEFECT_CODE_SUM;
	}

	public String getPRODUCTION_PRODUCT_CODE() {
		return PRODUCTION_PRODUCT_CODE;
	}

	public void setPRODUCTION_PRODUCT_CODE(String pRODUCTION_PRODUCT_CODE) {
		PRODUCTION_PRODUCT_CODE = pRODUCTION_PRODUCT_CODE;
	}

	public String getPRODUCT_ITEM_NAME() {
		return PRODUCT_ITEM_NAME;
	}

	public void setPRODUCT_ITEM_NAME(String pRODUCT_ITEM_NAME) {
		PRODUCT_ITEM_NAME = pRODUCT_ITEM_NAME;
	}

	public String getPRODUCTION_MOLD_INFO_CODE() {
		return PRODUCTION_MOLD_INFO_CODE;
	}

	public void setPRODUCTION_MOLD_INFO_CODE(String pRODUCTION_MOLD_INFO_CODE) {
		PRODUCTION_MOLD_INFO_CODE = pRODUCTION_MOLD_INFO_CODE;
	}

	public String getPRODUCTION_MOLD_INFO_NAME() {
		return PRODUCTION_MOLD_INFO_NAME;
	}

	public void setPRODUCTION_MOLD_INFO_NAME(String pRODUCTION_MOLD_INFO_NAME) {
		PRODUCTION_MOLD_INFO_NAME = pRODUCTION_MOLD_INFO_NAME;
	}

	public String getPRODUCTION_USER_NAME() {
		return PRODUCTION_USER_NAME;
	}

	public void setPRODUCTION_USER_NAME(String pRODUCTION_USER_NAME) {
		PRODUCTION_USER_NAME = pRODUCTION_USER_NAME;
	}

	public String getPRODUCTION_USER_CODE() {
		return PRODUCTION_USER_CODE;
	}

	public void setPRODUCTION_USER_CODE(String pRODUCTION_USER_CODE) {
		PRODUCTION_USER_CODE = pRODUCTION_USER_CODE;
	}

	public String getPRODUCTION_MODIFY_D() {
		return PRODUCTION_MODIFY_D;
	}

	public void setPRODUCTION_MODIFY_D(String pRODUCTION_MODIFY_D) {
		PRODUCTION_MODIFY_D = pRODUCTION_MODIFY_D;
	}

	public String getPRODUCTION_CC() {
		return PRODUCTION_CC;
	}

	public void setPRODUCTION_CC(String pRODUCTION_CC) {
		PRODUCTION_CC = pRODUCTION_CC;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public Integer getPRODUCTION_WorkOrder_No() {
		return PRODUCTION_WorkOrder_No;
	}

	public void setPRODUCTION_WorkOrder_No(Integer pRODUCTION_WorkOrder_No) {
		PRODUCTION_WorkOrder_No = pRODUCTION_WorkOrder_No;
	}

	public String getPRODUCTION_WorkOrder_ONo() {
		return PRODUCTION_WorkOrder_ONo;
	}

	public void setPRODUCTION_WorkOrder_ONo(String pRODUCTION_WorkOrder_ONo) {
		PRODUCTION_WorkOrder_ONo = pRODUCTION_WorkOrder_ONo;
	}

	public String getPRODUCTION_Date() {
		return PRODUCTION_Date;
	}

	public void setPRODUCTION_Date(String pRODUCTION_Date) {
		PRODUCTION_Date = pRODUCTION_Date;
	}
}
