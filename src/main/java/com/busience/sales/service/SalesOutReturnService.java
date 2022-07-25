package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.sales.dao.SalesOutputDao;
import com.busience.sales.dto.Sales_OutMat_tbl;

@Service
public class SalesOutReturnService {
	
	@Autowired
	SalesOutputDao salesOutputDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 판매 반품 저장
	public int salesOutReturnInsert(List<Sales_OutMat_tbl> salesOutReturnDtoList, String Modifier) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					Sales_OutMat_tbl sales_OutMat_tbl = new Sales_OutMat_tbl();
					
					// TODO Auto-generated method stub
					String LM_LotNo = null;
					String LT_LotNo = null;
					Double LM_Qty = 0.0;
					Double LT_Qty = 0.0;
					String LT_ItemCode = null;
					String LT_Before = null;
					String LT_After = null;
					String LT_Send_Clsfc = null;
					
					LT_Before = "";
					LT_After = "52";
					
					for(int i=0; i<salesOutReturnDtoList.size(); i++) {
						// LotMaster 대포장 +반품수량
						LM_LotNo = salesOutReturnDtoList.get(i).getSales_OutMat_Lot_No();
						LT_LotNo = salesOutReturnDtoList.get(i).getSales_OutMat_Lot_No();
						LM_Qty = (double) salesOutReturnDtoList.get(i).getSales_OutMat_Qty();
						LT_Qty = (double) salesOutReturnDtoList.get(i).getSales_OutMat_Qty();
						LT_ItemCode = salesOutReturnDtoList.get(i).getSales_OutMat_Code();
						LT_Send_Clsfc = "213";
						
						lotMasterDao.lotMasterUpdateDao((-1)*LM_Qty, LM_LotNo, LT_After);
						// LotTrans insert 대포장 +반품수량(" "->영업)
						// lotTrans 순번 조회
						int LT_No = lotTransDao.lotTransNoSelectDao2(LM_LotNo);
						
						lotTransDao.lotTransInsertDao2(LT_No, LT_LotNo, LT_ItemCode, LT_Qty, LT_Before, LT_After, LT_Send_Clsfc);
						// Stock update + 반품수량
						stockDao.stockReturnUpdateDao((-1)*LM_Qty, LT_ItemCode, LT_After);
						// sales_OutMat_tbl insert "-반품수량"
						sales_OutMat_tbl.setSales_OutMat_Cus_No(salesOutReturnDtoList.get(i).getSales_OutMat_Cus_No());
						sales_OutMat_tbl.setSales_OutMat_Lot_No(LT_LotNo);
						sales_OutMat_tbl.setSales_OutMat_Code(LT_ItemCode);
						sales_OutMat_tbl.setSales_OutMat_Qty(-1*salesOutReturnDtoList.get(i).getSales_OutMat_Qty());
						sales_OutMat_tbl.setSales_OutMat_Unit_Price(salesOutReturnDtoList.get(i).getSales_OutMat_Unit_Price());
						sales_OutMat_tbl.setSales_OutMat_Client_Code(salesOutReturnDtoList.get(i).getSales_OutMat_Client_Code());
						sales_OutMat_tbl.setSales_OutMat_Send_Clsfc(LT_Send_Clsfc);
						sales_OutMat_tbl.setSales_OutMat_Date(salesOutReturnDtoList.get(i).getSales_OutMat_Date());
						sales_OutMat_tbl.setSales_OutMat_Modifier(Modifier);
						
						salesOutputDao.salesOutMatInsertDao(sales_OutMat_tbl);
						
					}
				}
				
			});
			
			return 1;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
