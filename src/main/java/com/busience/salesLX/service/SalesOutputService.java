package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.dao.SalesOutputDao;
import com.busience.salesLX.dao.SalesOutputOrderListDao;
import com.busience.salesLX.dao.SalesOutputOrderMasterDao;
import com.busience.salesLX.dto.SalesOutputOrderMasterDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@Service
public class SalesOutputService {

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	SalesOutputDao salesOutputDao;
	
	@Autowired
	SalesOutputOrderListDao salesOutputOrderListDao;
	
	@Autowired
	SalesOutputOrderMasterDao salesOutputOrderMasterDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 영업 LotMaster 조회
	public List<LotMasterDto> salesOutputLotMasterDao(SearchDto searchDto) {
		return lotMasterDao.salesOutputLotMasterDao(searchDto);
	}
	
	// salesOutMat insert
	public int salesOutMatInsert(SalesOutputOrderMasterDto salesOutputOrderMasterDto, List<Sales_OutMat_tbl> sales_OutMat_List,
			String Modifier) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					Sales_OutMat_tbl sales_OutMat_tbl = new Sales_OutMat_tbl();
					String Sales_OutMat_Cus_No = salesOutputOrderMasterDto.getSales_Output_Order_mCus_No();
					Double LM_Qty = 0.0; 
					String LM_LotNo = null; 
					
					for(int i=0; i < sales_OutMat_List.size(); i++) {
						System.out.println(sales_OutMat_List.get(i));
						// sales_OutMatNo_create
						sales_OutMat_tbl = sales_OutMat_List.get(i);
						sales_OutMat_tbl.setSales_OutMat_Cus_No(Sales_OutMat_Cus_No);
						sales_OutMat_tbl.setSales_OutMat_Modifier(Modifier);
						sales_OutMat_tbl.setSales_OutMat_Order_No(Sales_OutMat_Cus_No);
						salesOutputDao.salesOutMatNoCreateDao(sales_OutMat_tbl);
						System.out.println("sales_OutMat_tbl = " + sales_OutMat_tbl);
						
						// sales_Output_OrderList_update
						salesOutputOrderListDao.salesOutputOrderListUpdateDao(sales_OutMat_tbl);
					
						// LotMaster_update
						LM_Qty = (double) sales_OutMat_tbl.getSales_OutMat_Qty();
						LM_LotNo = sales_OutMat_tbl.getSales_OutMat_Lot_No();
						lotMasterDao.lotMasterUpdateDao(LM_Qty, LM_LotNo);
						
						// LotTrans_insert
						lotTransDao.lotTransInsertDao2(sales_OutMat_tbl.getSales_OutMat_No(), sales_OutMat_tbl.getSales_OutMat_Lot_No(), sales_OutMat_tbl.getSales_OutMat_Code(), (double) sales_OutMat_tbl.getSales_OutMat_Qty(), sales_OutMat_tbl.getSales_OutMat_Before(), sales_OutMat_tbl.getSales_OutMat_After(), sales_OutMat_tbl.getSales_OutMat_Send_Clsfc());
						
						// sales_OutMat_insert
						salesOutputDao.salesOutMatInsertDao(sales_OutMat_tbl);
						
						// stockMat_update
						stockDao.stockUpdateDao((double) sales_OutMat_tbl.getSales_OutMat_Qty(), sales_OutMat_tbl.getSales_OutMat_Code(), sales_OutMat_tbl.getSales_OutMat_WareHouse());
						
						// sales_Output_OrderMaster_update
						salesOutputOrderMasterDao.salesOutputOrderMasterUpdateDao(salesOutputOrderMasterDto.getSales_Output_Order_mOrder_No());
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
