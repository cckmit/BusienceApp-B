package com.busience.standard.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Override
	public List<PRODUCT_INFO_TBL> selectItemList() {
		return itemDao.selectItemList();
	}
	
	@Override
	public int insertItemCode(PRODUCT_INFO_TBL array){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					itemDao.insertItemCode(array);

					//품목이 완제품일경우 (28) 영업 재고테이블에 저장
					if(array.getPRODUCT_MTRL_CLSFC().equals("28")) {
						itemDao.insertItemInSalesStock(array.getPRODUCT_ITEM_CODE());
					}
					//그 외 일경우 자재 재고테이블에 저장
					else {
						itemDao.insertItemInStock(array.getPRODUCT_ITEM_CODE());
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public int updateItemCode(PRODUCT_INFO_TBL array) {
		return itemDao.updateItemCode(array);
	}

	@Override
	public int deleteItemCode(String string) {
		return itemDao.deleteItemCode(string);
	}
	
}
