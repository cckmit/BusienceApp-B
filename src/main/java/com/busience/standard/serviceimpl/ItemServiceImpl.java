package com.busience.standard.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;
	
	@Override
	public List<PRODUCT_INFO_TBL> selectItemList() {
		return itemDao.selectItemList();
	}
	
	@Override
	public int insertItemCode(PRODUCT_INFO_TBL array) {
		return itemDao.insertItemCode(array);
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
