package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.dao.SalesOutputDao;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Service
public class SalesOutputService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	SalesOutputDao salesOutputDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 영업 LotMaster 조회
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto) {
		return lotMasterDao.salesOutputLotMasterDao(searchDto);
	}
	
	// salesOutMat insert
	public int salesOutMatInsert(Sales_OutMat_tbl sales_OutMat_tbl, List<SalesOutputOrderMasterDto> salesOutputOrderMasterDtoList,
			String Modifier) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					
					// sales_OutMatNo_create
					
					// sales_Output_OrderList_update
					
					// LotMaster_update
					
					// LotTranse_insert
					
					// sales_OutMat_insert
					
					// stockMat_update
					
					// sales_Output_OrderMaster_update
					
				}
				
			});
			
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
}
