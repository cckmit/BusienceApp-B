package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.busience.standard.dto.PRODUCT_INFO_TBL;

@Mapper
public interface ItemDao {
	
	//조회
	public List<PRODUCT_INFO_TBL>selectItemList();
	
	//등록
	public int insertItemCode(PRODUCT_INFO_TBL product_INFO_TBL);
	
	//제품 등록시 영업 재고테이블에 등록
	public int insertItemInSalesStock(@Param("itemCode") String itemCode);
	
	//제품 등록시 자재 재고테이블에 등록
	public int insertItemInStock(@Param("itemCode") String itemCode);

	//수정
	public int updateItemCode(PRODUCT_INFO_TBL product_INFO_TBL);
	
	//삭제
	public int deleteItemCode(@Param("itemCode") String itemCode);
}
