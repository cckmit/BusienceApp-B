package com.busience.material.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StockMatDto {
	private String SM_Code; //재고코드
	private String SM_Name; //재고명
	private String SM_STND_1; //재고규격1
	private int SM_Last_Qty; //전월 재고 수량
	private int SM_In_Qty; //당월 입고 수량
	private int SM_Out_Qty; //당월 출고 수량
	private int SM_Qty; // SM_Output_Qty - SM_Input_Qty 당월 재고량
	private String SM_Prcs_Date; //처리연월
}
