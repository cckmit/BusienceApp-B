package com.busience.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;

@Mapper
public interface InMatDao {
	
	//lot 순번 가져오기
	public int inMatNoSelectDao(String InMat_Lot_No);
	
	//자재 입고 등록
	public int inMatInsertDao(InMatDto inMatDto);
	
	//자재 입고 등록
	public int inMatInsert2Dao(@Param("InMat_Order_No") String InMat_Order_No, @Param("InMat_Lot_No") String InMat_Lot_No,
							@Param("InMat_Code") String InMat_Code, @Param("InMat_Qty") double InMat_Qty,
							@Param("InMat_Unit_Price") double InMat_Unit_Price, @Param("InMat_Price") double InMat_Price,
							@Param("InMat_Client_Code") String InMat_Client_Code, @Param("InMat_Date") String InMat_Date,
							@Param("InMat_Rcv_Clsfc") String InMat_Rcv_Clsfc, @Param("InMat_Modifier") String InMat_Modifier);
	
	//입고 검사 상태 수정
	public int inMatCheckUpdateDao(InMatDto inMatDto);
	
	//입고 리스트 조회
	public List<InMatDto> inMatListDao(SearchDto searchDto);
	
	//입고 리스트 조건별 조회
	public List<InMatDto> inMatOtherListDao(SearchDto searchDto);
	
	//납품 명세서 거래처 리스트
	public List<InMatDto> inMatDeliveryMasterDao(SearchDto searchDto);
	
	//납품 명세서 세부 리스트
	public List<InMatDto> inMatDeliverySubDao(SearchDto searchDto);
	
	//입고 반품 리스트
	public List<InMatDto> inMatReturnSelectDao(SearchDto searchDto);
}
