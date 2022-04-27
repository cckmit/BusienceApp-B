package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.material.dao.LotMasterDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

@Service
public class SalesInputService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// LotMaster select
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto) {
		return lotMasterDao.salesInputLotMasterSelectDao(lotMasterDto);
	}
	
    // salesInput insert
	public int salesInputInsert(Sales_InMat_tbl sales_InMat_tbl, List<SalesPackingDto> SalesPackingDtoList, int totalQty, String Modifier) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			return 1;
			
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
