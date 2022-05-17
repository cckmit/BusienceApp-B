package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dao.SalesOrderListDao;
import com.busience.sales.dao.SalesOrderMasterDao;
import com.busience.sales.dto.SalesOrderListDto;
import com.busience.sales.dto.SalesOrderMasterDto;

@Service
public class SalesOrderListService {

	@Autowired
	SalesOrderListDao salesOrderListDao;
	
	@Autowired
	SalesOrderMasterDao salesOrderMasterDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// SalesOrderMaster select
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto) {
		return salesOrderMasterDao.salesOrderMasterSelectDao(searchDto); 
	}
	
	// SalesOrderList select
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto) {
		return salesOrderListDao.salesOrderListSelectDao(searchDto);
	}
	
	// SalesOrderMasterInsertupdate
	public int salesOrderInsertUpdate(SalesOrderMasterDto salesOrderMasterDto, List<SalesOrderListDto> salesOrderListDtoList, String Modifier) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 수주번호가 없을 경우 수주번호 생성
					if(salesOrderMasterDto.getSales_Order_mCus_No().length() == 0) {
						// salesOrderNo insert
						salesOrderMasterDto.setSales_Order_mCus_No(salesOrderMasterDao.salesOrderNoCreateDao(salesOrderMasterDto));
					}
					
					salesOrderMasterDto.setSales_Order_mModifier(Modifier);
					// master insert
					salesOrderMasterDao.salesOrderMasterInsertUpdateDao(salesOrderMasterDto);
					// list insert
					for(int i=0; i < salesOrderListDtoList.size(); i++) {
						SalesOrderListDto salesOrderListDto = salesOrderListDtoList.get(i);
						salesOrderListDto.setSales_Order_lCus_No(salesOrderMasterDto.getSales_Order_mCus_No());
						
						salesOrderListDao.salesOrderListInsertUpdateDao(salesOrderListDto);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesOrderList delete
	public int salesOrderListDeleteDao(List<SalesOrderListDto> salesOrderDtoList) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					String Sales_Order_lCus_No = salesOrderDtoList.get(0).getSales_Order_lCus_No();
					// salesOrderList delete
					salesOrderListDao.salesOrderListDeleteDao(salesOrderDtoList, Sales_Order_lCus_No);
					
					// salesOrderMaster delete
					salesOrderMasterDao.salesOrderMasterDeleteDao(Sales_Order_lCus_No);
					
					// salesOrderList 순번 업데이트
					salesOrderListDao.salesOrderListNoUpdateDao(Sales_Order_lCus_No);
				}
				
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		 
	}
	
	
}
