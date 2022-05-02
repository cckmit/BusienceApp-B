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
import com.busience.salesLX.dao.SalesInputDao;
import com.busience.salesLX.dao.SalesPackingDao;
import com.busience.salesLX.dto.SalesPackingDto;
import com.busience.salesLX.dto.Sales_InMat_tbl;

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
	public List<Sales_InMat_tbl> salesInMatReturnSelectDao(SearchDto searchDto) {
		return salesInputDao.salesInMatReturnSelectDao(searchDto);
	}
	
	// 입고 반품 저장
	public int salesInReturnInsert(List<SalesPackingDto> salesInReturnDtoList) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					String LM_LotNo = null;
					String LT_LotNo = null;
					Double LM_Qty = 0.0;
					Double LT_Qty = 0.0;
					String LM_ItemCode = null;
					String LT_ItemCode = null;
					String LT_Before = null;
					String LT_After = null;
					String LT_Send_Clsfc = null;
					
					LT_Before = "51";
					LT_After = "52";
					
					for (int i=0; i<salesInReturnDtoList.size(); i++) {
						LM_LotNo = salesInReturnDtoList.get(i).getSales_Small_Packing_LotNo();
						LM_Qty = (double) salesInReturnDtoList.get(i).getSales_Packing_Qty();
						LT_Qty = (double) salesInReturnDtoList.get(i).getSales_Packing_Qty();
						LM_ItemCode = salesInReturnDtoList.get(i).getSales_Packing_Code();
						LT_ItemCode = salesInReturnDtoList.get(i).getSales_Packing_Code();
						LT_Send_Clsfc = salesInReturnDtoList.get(i).getSales_Packing_Status();
						
						// lotMaster 소포장 생산창고 +
						lotMasterDao.lotMasterUpdateDao(LM_Qty, LM_LotNo);;
						// stock 소포장 반품수량 +
						stockDao.stockUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
						// sales_packing_tbl 상태 '입고반품'
						salesPackingDao.salesPackingUpdateDao(salesInReturnDtoList.get(i));
						LM_LotNo = salesInReturnDtoList.get(i).getSales_Large_Packing_LotNo();
						LT_LotNo = salesInReturnDtoList.get(i).getSales_Large_Packing_LotNo();
					}
					// lotMaster 대포장 영업창고 -
					lotMasterDao.lotMasterUpdateDao(-1*LM_Qty, LM_LotNo);
					// lotTrans 순번 조회
					int LT_No = lotTransDao.lotTransNoSelectDao2(LM_LotNo);
					// lotTrans insert 대포장 반품수량 +
					lotTransDao.lotTransInsertDao2(LT_No, LT_LotNo, LT_ItemCode, LT_Qty, LT_Before, LT_After, LT_Send_Clsfc);
					// stock 대포장 반품수량 -
					stockDao.stockUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
					// sales_inMat_tbl 반품수량 -
					
					
				}
				
			});
			
			
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
