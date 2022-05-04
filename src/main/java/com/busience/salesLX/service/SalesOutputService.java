package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
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
	DtlDao dtlDao;

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
	
	// salesOutMat 조회
	public List<Sales_OutMat_tbl> salesOutMatSelectDao(Sales_OutMat_tbl sales_OutMat_tbl) {
		return salesOutputDao.salesOutMatSelectDao(sales_OutMat_tbl);
	}
	
	// salesOutMat insert
	public int salesOutMatInsert(SalesOutputOrderMasterDto salesOutputOrderMasterDto, List<Sales_OutMat_tbl> sales_OutMat_List,
			String Modifier) {
		
		try {
			
			// 창고 정보
			List<DtlDto> wareHouseList = dtlDao.findByCode(10);
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					Sales_OutMat_tbl sales_OutMat_tbl = new Sales_OutMat_tbl();
					String Sales_OutMat_Cus_No = salesOutputOrderMasterDto.getSales_Output_Order_mCus_No();
					String Sales_OutMat_Order_No = salesOutputOrderMasterDto.getSales_Output_Order_mOrder_No();
					int LT_No = 0;
					Double LM_Qty = 0.0; 
					String LM_LotNo = null; 
					String LT_LotNo = null;
					String LT_ItemCode = null;
					String LT_Before = null;
					String LT_After = null;
					String LT_Send_Clsfc = null;
					String LM_ItemCode = null;
					String LM_WareHouse = null;
					
				
					
					for(int i=0; i < sales_OutMat_List.size(); i++) {
						// sales_OutMatNo_create
						sales_OutMat_tbl = sales_OutMat_List.get(i);
						sales_OutMat_tbl.setSales_OutMat_Cus_No(Sales_OutMat_Cus_No);
						sales_OutMat_tbl.setSales_OutMat_Modifier(Modifier);
						sales_OutMat_tbl.setSales_OutMat_Order_No(Sales_OutMat_Order_No);
						salesOutputDao.salesOutMatNoCreateDao(sales_OutMat_tbl);
						
						// sales_Output_OrderList_update
						salesOutputOrderListDao.salesOutputOrderListUpdateDao(sales_OutMat_tbl);
					
						// LotMaster_update
						LM_Qty = (double) sales_OutMat_tbl.getSales_OutMat_Qty();
						LM_LotNo = sales_OutMat_tbl.getSales_OutMat_Lot_No();
						
						sales_OutMat_tbl.getSales_OutMat_No();
						lotMasterDao.lotMasterUpdateDao(-1*LM_Qty, LM_LotNo);
						
						// LotTrans_insert
						LT_LotNo = sales_OutMat_tbl.getSales_OutMat_Lot_No();
						// 랏트랜스 번호 가져오기
						int LotTransNo = lotTransDao.lotTransNoSelectDao2(LT_LotNo);
						sales_OutMat_tbl.setLT_No(LotTransNo);
						LT_No = sales_OutMat_tbl.getLT_No();
						
						// LT_ItemCode
						LT_ItemCode = sales_OutMat_tbl.getSales_OutMat_Code();
						
						// LT_Before, After  Before -> 52, After -> 공백(없음)
						// 생산창고 -> 제품창고
						LT_Before = wareHouseList.get(2).getCHILD_TBL_NO();
					    LT_After = "";
					    // warehouse Send_Clsfc
					    LT_Send_Clsfc = sales_OutMat_tbl.getSales_OutMat_Send_Clsfc();
						
						lotTransDao.lotTransInsertDao2(LT_No, LT_LotNo, LT_ItemCode, LM_Qty, LT_Before, LT_After, LT_Send_Clsfc);
						
						sales_OutMat_tbl.setSales_OutMat_Client_Code(salesOutputOrderMasterDto.getSales_Output_Order_mCode());
						
						// sales_OutMat_insert
						salesOutputDao.salesOutMatInsertDao(sales_OutMat_tbl);
						
						// stockMat_update
						LM_ItemCode = sales_OutMat_tbl.getSales_OutMat_Code();
						LM_WareHouse = wareHouseList.get(2).getCHILD_TBL_NO();
						stockDao.stockUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
						
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
	
	// selecOutMat List select
	public List<Sales_OutMat_tbl> salesOutMatSelectListDao(Sales_OutMat_tbl sales_OutMat_tbl, SearchDto searchDto) {
		
		String Sales_OutMat_Code = sales_OutMat_tbl.getSales_OutMat_Code();
		String Sales_OutMat_Client_Code = sales_OutMat_tbl.getSales_OutMat_Client_Code();
		String Sales_OutMat_Send_Clsfc = sales_OutMat_tbl.getSales_OutMat_Send_Clsfc();
		String Sales_OutMat_Lot_No = sales_OutMat_tbl.getSales_OutMat_Lot_No();
		String startDate = searchDto.getStartDate();
		String endDate = searchDto.getEndDate();
		
		return salesOutputDao.salesOutMatSelectListDao(Sales_OutMat_Code, Sales_OutMat_Client_Code, Sales_OutMat_Send_Clsfc, Sales_OutMat_Lot_No, startDate, endDate);
	}
	
	// salesOutMat Item View
	public List<Sales_OutMat_tbl> salesOutMatItemViewDao(Sales_OutMat_tbl sales_OutMat_tbl, SearchDto searchDto) {
		
		String Sales_OutMat_Code = sales_OutMat_tbl.getSales_OutMat_Code();
		String Sales_OutMat_Send_Clsfc = sales_OutMat_tbl.getSales_OutMat_Send_Clsfc();
		String startDate = searchDto.getStartDate();
		String endDate = searchDto.getEndDate();
		
		List<Sales_OutMat_tbl> salesOutMatList = salesOutputDao.salesOutMatItemViewDao(Sales_OutMat_Code, Sales_OutMat_Send_Clsfc, startDate, endDate);
		
		for(int i=0; i < salesOutMatList.size(); i++) {
			String itemCode = salesOutMatList.get(i).getSales_OutMat_Code();
			String salesOutMatDate = salesOutMatList.get(i).getSales_OutMat_Date();
			
			if(itemCode == null || salesOutMatDate == null) {
				salesOutMatList.get(i).setSales_OutMat_Lot_No("Sub Total");
				salesOutMatList.get(i).setSales_OutMat_Date("");
				salesOutMatList.get(i).setSales_OutMat_Send_Clsfc("");
				salesOutMatList.get(i).setSales_OutMat_STND_1("");
				salesOutMatList.get(i).setSales_OutMat_UNIT("");
				salesOutMatList.get(i).setSales_OutMat_Item_Clsfc_Name_1("");
			} 
			
			if(itemCode == null && salesOutMatDate == null) {
				salesOutMatList.get(i).setSales_OutMat_Lot_No("Grand Total");
				salesOutMatList.get(i).setSales_OutMat_Send_Clsfc("");
				salesOutMatList.get(i).setSales_OutMat_Name("");
			}
		}
		
		return salesOutMatList;
	}
	
	
}
