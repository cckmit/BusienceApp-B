package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;

@Service
public class ItemService {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//조회
	public List<ItemDto> selectItemList() {
		return itemDao.selectItemList();
	}
	
	//등록
	public int insertItemCode(ItemDto itemDto){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					itemDto.setPRODUCT_MODIFIER(authentication.getName());
					
					itemDao.insertItemCode(itemDto);

					//품목이 완제품일경우 (28) 영업 재고테이블에 저장
					if(itemDto.getPRODUCT_MTRL_CLSFC().equals("28")) {
						itemDao.insertItemInSalesStock(itemDto.getPRODUCT_ITEM_CODE());
					}
					//그 외 일경우 자재 재고테이블에 저장
					else {
						itemDao.insertItemInStock(itemDto.getPRODUCT_ITEM_CODE());
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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
