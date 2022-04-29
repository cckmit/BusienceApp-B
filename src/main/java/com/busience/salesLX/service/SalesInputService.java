package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.salesLX.dao.SalesInputDao;
import com.busience.salesLX.dao.SalesPackingDao;
import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

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
					
					// lotMaster tbl insert
					lotMasterDao.salesLotMasterInsertDao(sales_InMat_tbl);
					
					// lotTrans tbl insert(순번 조회, 생산창고 51 -> 영업창고 52)
					LT_LotNo = LargeLotNo;
					int LotTranseNo = lotTransDao.lotTransNoSelectDao2(LT_LotNo);
					
					sales_InMat_tbl.setSales_InMat_No(LotTranseNo);
					
					LM_Qty = (double) sales_InMat_tbl.getSales_InMat_Qty();
					LT_Before = sales_InMat_tbl.getSales_InMat_Before();
					LT_After = sales_InMat_tbl.getSales_InMat_After();
					LT_Send_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
					
					lotTransDao.lotTransInsertDao2(LotTranseNo, LT_LotNo, LT_ItemCode, LM_Qty, LT_Before, LT_After, LT_Send_Clsfc);
					
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
						
					}
					
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
}
