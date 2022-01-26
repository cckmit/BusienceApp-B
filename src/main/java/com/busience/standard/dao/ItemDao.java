package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.ItemDto;

@Mapper
public interface ItemDao {
	
	//조회
	public List<ItemDto> selectItemList();
	
	//등록
	public int insertItemCode(ItemDto itemDto);
	
	//제품 등록시 영업 재고테이블에 등록
	public int insertItemInSalesStock(String itemCode);
	
	//제품 등록시 자재 재고테이블에 등록
	public int insertItemInStock(String itemCode);

	//수정
	public int updateItemCode(ItemDto itemDto);
	
	//삭제
	public int deleteItemCode(String itemCode);
	
	//품목코드로 검색
	public ItemDto selectItemCode(String itemCode);
	
	//자재분류로 검색
	public List<ItemDto> selectMaterialClsfc(String materialClsfc);
}
