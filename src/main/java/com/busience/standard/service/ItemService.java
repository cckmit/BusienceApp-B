package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busience.common.dao.DtlDao;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;

@Service
public class ItemService {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	DtlDao dtlDao;
	
	//조회
	public List<ItemDto> selectItemList() {
		return itemDao.selectItemList();
	}
	
	//품목별 조회
	public ItemDto selectItemCode(String itemCode) {
		return itemDao.selectItemCode(itemCode);
	}
	
	//제품분류별 조회
	public List<ItemDto> selectMaterialClsfc(String materialClsfc) {		
		return itemDao.selectMaterialClsfc(materialClsfc);
	}
	
	//완제품 코드 다음 값 검색
	public ItemDto itemCodeNextVal() {
		return itemDao.itemCodeNextVal();
	}

	//등록
	public int insertItemCode(ItemDto itemDto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		itemDto.setPRODUCT_MODIFIER(authentication.getName());
		
		return itemDao.insertItemCode(itemDto);
	}

	//수정
	public int updateItemCode(ItemDto itemDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		itemDto.setPRODUCT_MODIFIER(authentication.getName());
		
		return itemDao.updateItemCode(itemDto);
	}

	//삭제
	public int deleteItemCode(String itemCode) {
		return itemDao.deleteItemCode(itemCode);
	}
}
