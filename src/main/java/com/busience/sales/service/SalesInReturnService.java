package com.busience.sales.service;

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
import com.busience.sales.dao.SalesInputDao;
import com.busience.sales.dao.SalesPackingDao;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.dto.Sales_InMat_tbl;

@Service
public class SalesInReturnService {

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
	
	// 입고 반품 조회
	public List<Sales_InMat_tbl> salesInMatReturnListDao(SearchDto searchDto) {
		return salesInputDao.salesInMatReturnListDao(searchDto);
	}
	
	// 입고 반품 저장
	public int salesInReturnInsert(List<SalesPackingDto> salesInReturnDtoList, String Modifier) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					String LM_LotNo = null;
					String LT_LotNo = null;
					Double LM_Qty = 0.0;
					Double LT_Qty = 0.0;
					// Qty 합계
					Double LM_SumQty = 0.0;
					Double LT_SumQty = 0.0;
					int Sales_InMat_Qty = 0;
					String LM_ItemCode = null;
					String LT_ItemCode = null;
					String LT_Before = null;
					String LT_After = null;
					String LT_Send_Clsfc = null;
					
					Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
					
					LT_Before = "51";
					LT_After = "52";
					
					for (int i=0; i<salesInReturnDtoList.size(); i++) {
						LM_LotNo = salesInReturnDtoList.get(i).getSales_Small_Packing_LotNo();
						LM_Qty = (double) salesInReturnDtoList.get(i).getSales_Packing_Qty();
						LT_Qty = (double) salesInReturnDtoList.get(i).getSales_Packing_Qty();
						Sales_InMat_Qty += salesInReturnDtoList.get(i).getSales_Packing_Qty();
						
						LM_ItemCode = salesInReturnDtoList.get(i).getSales_Packing_Code();
						LT_ItemCode = salesInReturnDtoList.get(i).getSales_Packing_Code();
						
						// lotMaster 소포장 생산창고 +
						lotMasterDao.lotMasterUpdateDao(LM_Qty, LM_LotNo);;
						// stock 소포장 반품수량 +
						stockDao.stockReturnUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
						// sales_packing_tbl 상태 '입고반품'
						salesPackingDao.salesPackingUpdateDao(salesInReturnDtoList.get(i));
						LM_LotNo = salesInReturnDtoList.get(i).getSales_Large_Packing_LotNo();
						LT_LotNo = salesInReturnDtoList.get(i).getSales_Large_Packing_LotNo();
					
						LM_SumQty += LM_Qty;
						LT_SumQty += LT_Qty;
					}
					
					// 영업에는 소박스 제품 수량 합계가 들어가야하기 때문에 sum을 해준다.
					LM_Qty = LM_SumQty;
					LT_Qty = LT_SumQty;
					LT_Send_Clsfc = "207";
					
					// lotMaster 대포장 영업창고 -
					lotMasterDao.lotMasterUpdateDao(-1*LM_Qty, LM_LotNo);
					// lotTrans 순번 조회
					int LT_No = lotTransDao.lotTransNoSelectDao2(LM_LotNo);
					// 대포장 영업 -> 생산
					LT_Before = "52";
					LT_After = "51";
					// lotTrans insert 대포장 반품수량 +
					lotTransDao.lotTransInsertDao2(LT_No, LT_LotNo, LT_ItemCode, LT_Qty, LT_Before, LT_After, LT_Send_Clsfc);
					// 되돌림
					LT_After = "52";
					LT_Before = "51";
					// stock 대포장 반품수량 -
					stockDao.stockUpdateDao((-1)*LM_Qty, LM_ItemCode, LT_After);
					
					sales_InMat_tbl.setSales_InMat_No(LT_No);
					sales_InMat_tbl.setSales_InMat_Qty(-1*Sales_InMat_Qty);
					sales_InMat_tbl.setSales_InMat_Code(LM_ItemCode);
					sales_InMat_tbl.setSales_InMat_Lot_No(LM_LotNo);
					sales_InMat_tbl.setSales_InMat_Rcv_Clsfc("207");
					sales_InMat_tbl.setSales_InMat_Modifier(Modifier);
					// sales_inMat_tbl 반품수량 -
					salesInputDao.salesInMatInsertDao(sales_InMat_tbl);
					
				}
				
			});
			
			
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
