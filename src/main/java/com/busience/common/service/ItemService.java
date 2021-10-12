package com.busience.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.busience.common.mapper.ItemMapper;
import com.busience.standard.Dto.PRODUCT_INFO_TBL;

@Service
@Transactional
public class ItemService {

	//mybatis
	private ItemMapper itemMapper;
			
	public ItemService(ItemMapper itemMapper) {
		this.itemMapper = itemMapper;
	}
	
	//등록
    public int insertItemCode(PRODUCT_INFO_TBL array) {
    	return itemMapper.insertItemCode(array);
    }
		
	//수정
    public int updateItemCode(PRODUCT_INFO_TBL array) {
    	return itemMapper.updateItemCode(array);
    }
    
    //삭제
    public int deleteItemCode(String string) {
    	return itemMapper.deleteItemCode(string);
    }
}