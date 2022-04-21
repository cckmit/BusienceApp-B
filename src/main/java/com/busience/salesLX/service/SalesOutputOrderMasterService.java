package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dao.SalesOrderListDao;
import com.busience.salesLX.dao.SalesOrderMasterDao;
import com.busience.salesLX.dao.SalesOutputOrderListDao;
import com.busience.salesLX.dao.SalesOutputOrderMasterDao;
import com.busience.salesLX.dto.SalesOutputOrderListDto;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;

@Service
public class SalesOutputOrderMasterService {

	@Autowired
	SalesOrderListDao salesOrderListDao;
	
	@Autowired
	SalesOrderMasterDao salesOrderMasterDao;
	
	@Autowired
	SalesOutputOrderMasterDao salesOutputOrderMasterDao;
	
	@Autowired
	SalesOutputOrderListDao salesOutputOrderListDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// salesOutputOrderMaster select
	public List<SalesOutputOrderMasterDto> salesOutputOrderMasterSelectDao(SearchDto searchDto) {
		return salesOutputOrderMasterDao.salesOutputOrderMasterSelectDao(searchDto);
	}
	
	// salesOutputOrderInsert
	public int salesOutputOrderInsert(SalesOutputOrderMasterDto salesOutputOrderMasterDto, List<SalesOutputOrderListDto> salesOutputOrderListDtoList,
			String Modifier) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					String Sales_Order_mCus_No = salesOutputOrderMasterDto.getSales_Output_Order_mCus_No();
					
					if(salesOutputOrderMasterDto.getSales_Output_Order_mOrder_No() == null) {
						// salesOutputOrderNo create
						salesOutputOrderMasterDto.setSales_Output_Order_mOrder_No(salesOutputOrderMasterDao.salesOutputOrderNoCreateDao(salesOutputOrderMasterDto));
					}
					
					salesOutputOrderMasterDto.setSales_Output_Order_mModifier(Modifier);
					
					// salesOutputOrderMaster insert
					salesOutputOrderMasterDao.salesOutputOrderMasterInsertDao(salesOutputOrderMasterDto);
					
					for(int i=0; i < salesOutputOrderListDtoList.size(); i++) {
						SalesOutputOrderListDto salesOutputOrderListDto = salesOutputOrderListDtoList.get(i);
						// salesOrderList update
						salesOrderListDao.salesOrderListUpdateDao(salesOutputOrderListDto);
						// salesOutputOrderList insert
						salesOutputOrderListDto.setSales_Output_Order_lOrder_No(salesOutputOrderMasterDto.getSales_Output_Order_mOrder_No());
						System.out.println("지시번호 = " + salesOutputOrderListDto.getSales_Output_Order_lOrder_No());
						salesOutputOrderListDao.salesOutputOrderListInsertDao(salesOutputOrderListDto);
					}
					// salesOrderMaster update
					salesOrderMasterDao.salesOrderMasterUpdateDao(Sales_Order_mCus_No);
					
				}
				
			});
			
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesOutputOrderMaster report
	public List<SalesOutputOrderMasterDto> salesOutputOrderListDao(SearchDto searchDto) {
		return salesOutputOrderMasterDao.salesOutputOrderListDao(searchDto);
	}
}
