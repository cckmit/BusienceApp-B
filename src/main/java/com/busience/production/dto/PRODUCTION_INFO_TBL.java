package com.busience.production.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
	private String PRODUCT_INFO_STND_2; // 규격
	private String PRODUCT_ITEM_CLSFC_1_NAME; // 규격
	private String PRODUCT_ITEM_CLSFC_2_NAME; // 규격

	private String TIME; // 이유 : 생산,불량현황 모니터링의 시간데이터를 받는 역할 / 이름 : 박성민

	private Integer PRODUCTION_WorkOrder_No;
	private String PRODUCTION_WorkOrder_ONo;
	private String PRODUCTION_Date;
}
