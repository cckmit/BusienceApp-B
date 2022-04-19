package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dao.SalesOrderMasterDao;
import com.busience.salesLX.dao.SalesOutputOrderMasterDao;
import com.busience.salesLX.dto.SalesOutputOrderListDto;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;

@Service
public class SalesOutputOrderMasterService {

	@Autowired
	SalesOutputOrderMasterDao salesOutputOrderMasterDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// salesOutputOrderMaster select
	public List<SalesOutputOrderMasterDto> salesOutputOrderMasterSelectDao(SearchDto searchDto) {
		return salesOutputOrderMasterDao.salesOutputOrderMasterSelectDao(searchDto);
	}
	
	// salesOutputOrderInsert
	public int salesOutputOrderInsert(SalesOutputOrderMasterDto salesOutputOrderMasterDto, List<SalesOutputOrderListDto> SalesOutputOrderListDtoList,
			String Modifier) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					if(salesOutputOrderMasterDto.getSales_Output_Order_mOrder_No().length() == 0) {
						// salesOutputOrderNo insert
						salesOutputOrderMasterDto.setSales_Output_Order_mOrder_No(salesOutputOrderMasterDao.salesOutputOrderNoCreateDao(salesOutputOrderMasterDto));
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
