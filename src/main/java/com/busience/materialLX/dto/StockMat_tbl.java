package com.busience.materialLX.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
	
	private String SM_CLSFC_1_Name;
}
