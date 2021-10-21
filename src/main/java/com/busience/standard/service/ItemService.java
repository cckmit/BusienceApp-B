package com.busience.standard.service;

import java.util.List;

import com.busience.standard.dto.PRODUCT_INFO_TBL;

public interface ItemService {

	//조회
	public List<PRODUCT_INFO_TBL>selectItemList();
	
	//등록
    public int insertItemCode(PRODUCT_INFO_TBL array);
		
	//수정
    public int updateItemCode(PRODUCT_INFO_TBL array);
    
    //삭제
    public int deleteItemCode(String string);
}
