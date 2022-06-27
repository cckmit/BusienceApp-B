package com.busience.sales.service;

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
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.sales.dao.SalesInputDao;
import com.busience.sales.dao.SalesPackingDao;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.dto.Sales_InMat_tbl;

@Service
public class SalesInputService {
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	SalesInputDao salesInputDao;

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
	@Autowired
	SalesPackingDao salesPackingDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// LotMaster select
	public List<LotMasterDto> salesInputLotMasterSelectDao(LotMasterDto lotMasterDto) {
		return lotMasterDao.salesInputLotMasterSelectDao(lotMasterDto);
	}
	
	// salesInput select
	public List<Sales_InMat_tbl> salesInMatSelectDao() {
		return salesInputDao.salesInMatSelectDao();
	}
	
    // salesInput insert
	public int salesInputInsert(Sales_InMat_tbl sales_InMat_tbl, List<SalesPackingDto> salesPackingDtoList, int totalQty, String Modifier) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
		
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					// TODO Auto-generated method stub
					String LT_LotNo = null; 
					String LT_ItemCode = null;
					String LM_ItemCode = null;
					String LM_Warehouse = null;
					String LM_LotNo = null;
					Double LM_Qty = 0.0;
					String LT_Before = null;
					String LT_After = null;
					String LT_Send_Clsfc = null;
					
					// 대포장 Lot 생성 - Large - 이니셜 'L'+날짜+제품+순번
					String LargeLotNo = salesInputDao.salesLotNoSelectDao(sales_InMat_tbl);
					//System.out.println(LargeLotNo);
					
					SalesPackingDto salesPackingDto = new SalesPackingDto();
					
					sales_InMat_tbl.setSales_InMat_Lot_No(LargeLotNo);
					sales_InMat_tbl.setSales_InMat_Qty(totalQty);
					sales_InMat_tbl.setSales_InMat_Before(wareHouseList.get(1).getCHILD_TBL_NO());
					sales_InMat_tbl.setSales_InMat_After(wareHouseList.get(2).getCHILD_TBL_NO());
					sales_InMat_tbl.setSales_InMat_WareHouse(wareHouseList.get(2).getCHILD_TBL_NO());
					LT_ItemCode = sales_InMat_tbl.getSales_InMat_Code();
					LM_ItemCode = sales_InMat_tbl.getSales_InMat_Code();
					LM_Warehouse = sales_InMat_tbl.getSales_InMat_WareHouse();
					
					// lotTrans tbl insert(순번 조회, 생산창고 51 -> 영업창고 52)
					LT_LotNo = LargeLotNo;
					LM_LotNo = LargeLotNo;
					int LotTranseNo = lotTransDao.lotTransNoSelectDao2(LT_LotNo);
					
					sales_InMat_tbl.setSales_InMat_No(LotTranseNo);
					
					// 랏번호 update
					salesInputDao.salesLotNoSalesUpdateDao();
					
					LM_Qty = (double) sales_InMat_tbl.getSales_InMat_Qty();
					LT_Before = sales_InMat_tbl.getSales_InMat_Before();
					LT_After = sales_InMat_tbl.getSales_InMat_After();
					LT_Send_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
					
					// lotMaster tbl insert
					lotMasterDao.salesLotMasterInsertUpdateDao(LM_LotNo, LM_ItemCode, LM_Qty, LM_Warehouse);
					
					lotTransDao.lotTransInsertDao2(LotTranseNo, LT_LotNo, LT_ItemCode, LM_Qty, LT_Before, LT_After, LT_Send_Clsfc);
					
					// no 하나 올려주기
					for(int i=0; i<salesPackingDtoList.size(); i++) {
						
						// sales_Packing_tbl insert - 대포장 lot번호 생성, 순번 조회 및 생성
						salesPackingDto = salesPackingDtoList.get(i);
						// large LotNo 설정
						salesPackingDto.setSales_Large_Packing_LotNo(LargeLotNo);
						// 순번 조회
						SalesPackingDto salesPackingNo = salesPackingDao.salesPackingNoCreateDao(salesPackingDto);
						salesPackingDto.setSales_Packing_No(salesPackingNo.getSales_Packing_No());
						
						// sales_Packing_tbl insert
						salesPackingDao.salesPackingInsertDao(salesPackingDto);
						
						LM_LotNo = salesPackingDto.getSales_Small_Packing_LotNo();
						LT_LotNo = salesPackingDto.getSales_Small_Packing_LotNo();
						
						LM_Qty = (double) salesPackingDto.getSales_Packing_Qty();
						
						// lotMaster_tbl_insertupdate
						lotMasterDao.lotMasterUpdateDao(-1*LM_Qty, LM_LotNo);
						
					}
					
					LM_Qty = (double) sales_InMat_tbl.getSales_InMat_Qty();
					
					sales_InMat_tbl.setSales_InMat_Modifier(Modifier);
					
					// sales inMat tbl insert
					salesInputDao.salesInMatInsertDao(sales_InMat_tbl);
					
					// stock tbl insert -> 창고 변경하여 insert
					stockDao.stockInsertDao(LM_ItemCode, LM_Qty, LM_Warehouse);
					// stock tbl update(기존 생산데이터 수량 차감)
					stockDao.stockUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
				}
				
			});
			
			return 1;
			
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	//salesInMat List select
	public List<Sales_InMat_tbl> salesInputListDao(Sales_InMat_tbl sales_InMat_tbl, SearchDto searchDto) {
		
		String Sales_InMat_Code = sales_InMat_tbl.getSales_InMat_Code();
		String Sales_InMat_Lot_No = sales_InMat_tbl.getSales_InMat_Lot_No();
		String Sales_InMat_Rcv_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
		String startDate = searchDto.getStartDate();
		String endDate = searchDto.getEndDate();
		
		return salesInputDao.salesInputListDao(Sales_InMat_Code, Sales_InMat_Lot_No, Sales_InMat_Rcv_Clsfc, startDate, endDate);
	}
	
	//salesInMat Item View
	public List<Sales_InMat_tbl> salesInputItemViewDao(Sales_InMat_tbl sales_InMat_tbl, SearchDto searchDto) {
		
		String Sales_InMat_Code = sales_InMat_tbl.getSales_InMat_Code();
		String Sales_InMat_Rcv_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
		String startDate = searchDto.getStartDate();
		String endDate = searchDto.getEndDate();
		
		List<Sales_InMat_tbl> salesInMatList = salesInputDao.salesInputItemViewDao(Sales_InMat_Code, Sales_InMat_Rcv_Clsfc, startDate, endDate);
		
		for(int i=0; i < salesInMatList.size(); i++) {
			String itemCode = salesInMatList.get(i).getSales_InMat_Code();
			String salesInMatDate = salesInMatList.get(i).getSales_InMat_Date();
			
			if(itemCode == null || salesInMatDate == null) {
				salesInMatList.get(i).setSales_InMat_Lot_No("Sub Total");
				salesInMatList.get(i).setSales_InMat_Date("");
				salesInMatList.get(i).setSales_InMat_Rcv_Clsfc("");
				salesInMatList.get(i).setSales_InMat_STND_1("");
				salesInMatList.get(i).setSales_InMat_UNIT("");
				salesInMatList.get(i).setSales_InMat_Item_Clsfc_1("");
				salesInMatList.get(i).setSales_InMat_Item_Clsfc_2("");
				salesInMatList.get(i).setSales_InMat_Item_Material("");
			} 
			
			if(itemCode == null && salesInMatDate == null) {
				salesInMatList.get(i).setSales_InMat_Lot_No("Grand Total");
				salesInMatList.get(i).setSales_InMat_Rcv_Clsfc("");
				salesInMatList.get(i).setSales_InMat_Name("");
				salesInMatList.get(i).setSales_InMat_Item_Clsfc_1("");
				salesInMatList.get(i).setSales_InMat_Item_Clsfc_2("");
				salesInMatList.get(i).setSales_InMat_Item_Material("");
			}
		}
		
		return salesInMatList;
	}
	
}
