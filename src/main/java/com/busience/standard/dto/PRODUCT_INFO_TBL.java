package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PRODUCT_INFO_TBL {
	int id;
	private String PRODUCT_BUSINESS_PLACE; // 사업장코드(세부테이블)
	private String PRODUCT_BUSINESS_PLACE_NAME; // 사업장명(세부테이블)
	private String PRODUCT_ITEM_CODE; // 품목코드(기본키)
	private String PRODUCT_OLD_ITEM_CODE; // 구품목코드
	private String PRODUCT_ITEM_NAME; // 품명
	private String PRODUCT_INFO_STND_1; // 규격1
	private String PRODUCT_INFO_STND_2; // 규격2
	private int PRODUCT_UNIT_PRICE; // 단가
	private int PRODUCT_MULTIPLE;
	private String PRODUCT_UNIT; // 단위코드(세부코드)
	private String PRODUCT_UNIT_NAME; // 단위명(세부코드)
	private String PRODUCT_MATERIAL; // 재질코드(세부코드)
	private String PRODUCT_MATERIAL_NAME; // 재질명(세부코드)
	private String PRODUCT_MTRL_CLSFC; // 자재분류코드 (세부코드)
	private String PRODUCT_MTRL_CLSFC_NAME; // 자재분류명 (세부코드)
	private String PRODUCT_ITEM_CLSFC_1; // 품목분류코드1 (세부코드)
	private String PRODUCT_ITEM_CLSFC_1_NAME; // 품목분류명1 (세부코드)
	private String PRODUCT_ITEM_CLSFC_2; // 품목분류코드2 (세부코드)
	private String PRODUCT_ITEM_CLSFC_2_NAME; // 품목분류명2 (세부코드)
	private String PRODUCT_SUBSID_MATL_MGMT; // 부자재관리
	private String PRODUCT_ITEM_STTS; // 품목상태코드 (세부코드)
	private String PRODUCT_ITEM_STTS_NAME; // 품목상태명 (세부코드)
	private String PRODUCT_BASIC_WAREHOUSE; // 기본창고코드 (세부코드)
	private String PRODUCT_BASIC_WAREHOUSE_NAME; // 기본창고명 (세부코드)
	private String PRODUCT_SAVE_AREA; // 보관구역
	private String PRODUCT_SAVE_AREA_NAME; // 보관구역명
	private int PRODUCT_SFTY_STOCK; // 안전재고
	private String PRODUCT_BUYER; // 구매담당자
	private String PRODUCT_WRHSN_INSPC; // 입고검사
	private String PRODUCT_USE_STATUS; // 사용유무
	private String PRODUCT_MODIFY_D; // 수정일자
	private String PRODUCT_MODIFIER; // 수정자
	private boolean BOM_Registered; // Bom 등록 여부
}
