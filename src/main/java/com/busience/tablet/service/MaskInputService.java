 package com.busience.tablet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class MaskInputService {
	
	@Autowired
	CrateLotDao crateLotDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<CrateLotDto> crateMachineSelect(SearchDto searchDto) {
		return crateLotDao.crateMachineSelectDao(searchDto);
	}
	
	public List<CrateLotDto> crateLotListSelect(SearchDto searchDto) {
		return crateLotDao.crateLotListSelectDao(searchDto);
	}
	
	public int crateLotUpdate2(CrateLotDto crateLotDto) {
		return crateLotDao.crateLotUpdateDao2(crateLotDto);
	}
	
	public int crateLotRefresh(CrateLotDto crateLotDto) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<CrateLotDto> refreshList = new ArrayList<CrateLotDto>();
					refreshList = crateLotDao.crateLotRefreshSelectDao(crateLotDto);
					
					for(int i=0;i<refreshList.size();i++) {
						crateLotDao.crateLotUpdateDao2(refreshList.get(i));
					}					
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
