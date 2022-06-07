package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.qc.dao.ItemPackingInspectDao;
import com.busience.qc.dto.ItemPackingInspectDto;

@Service
public class ItemPackingInspectService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	ItemPackingInspectDao itemPackingInspectDao;
	
	// 완제품 데이터 조회
	public List<LotMasterDto> salesItemListDao(SearchDto searchDto) {
		return lotMasterDao.salesItemListDao(searchDto);
	}
	
	// LotNo로 조회
	public List<ItemPackingInspectDto> itemPackingInspectListDao(SearchDto searchDto) {
		return itemPackingInspectDao.itemPackingInspectListDao(searchDto);
	}
	
	public int itemPackingInspectInsertDao(ItemPackingInspectDto itemPackingInspectDto) {
		return itemPackingInspectDao.itemPackingInspectInsertDao(itemPackingInspectDto);
	}
}
