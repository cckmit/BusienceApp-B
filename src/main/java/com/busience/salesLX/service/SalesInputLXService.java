package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.salesLX.dao.SalesInputLXDao;
import com.busience.salesLX.dto.Sales_InMat_tbl;
import com.busience.standard.dto.ItemDto;

@Service
public class SalesInputLXService {

	@Autowired
	SalesInputLXDao salesInputLXDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//조회
	public List<ItemDto> salesInputList() {
		return salesInputLXDao.salesInputListDao();
	}
	
	//등록
	public int salesInputRegister(List<Sales_InMat_tbl> sales_InMat_tbl_List, String userCode){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<sales_InMat_tbl_List.size();i++) {
						sales_InMat_tbl_List.get(i).setSales_InMat_Modifier(userCode);
						
						salesInputLXDao.salesInMatInsertDao(sales_InMat_tbl_List.get(i));
						
						salesInputLXDao.salesStockMatUpdateDao(sales_InMat_tbl_List.get(i));
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
