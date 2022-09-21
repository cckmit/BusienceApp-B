package com.busience.sales.service;

import java.util.ArrayList;
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
import com.busience.sales.dao.SalesInputDao;
import com.busience.sales.dao.SalesPackingDao;
import com.busience.sales.dto.SalesInMatDto;
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
	public List<LotMasterDto> salesInReturnLXSelect() {
		String warehouse = "52";
		return lotMasterDao.lotMasterItemSumSelectDao(warehouse);
	}
	
	// 입고 반품 조회
	public List<Sales_InMat_tbl> salesInMatReturnListDao(SearchDto searchDto) {
		return salesInputDao.salesInMatReturnListDao(searchDto);
	}
	
	public List<SalesInMatDto> salesInReturnListLXSelect(SearchDto searchDto) {
		return salesInputDao.salesInputListLXDao(searchDto);
	}
	
	public int salesInReturnSave(List<LotMasterDto> lotMasterDtoList, String userCode) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(LotMasterDto list : lotMasterDtoList) {
						SearchDto searchDto = new SearchDto();
						searchDto.setItemCode(list.getLM_ItemCode());
						searchDto.setWarehouse(list.getLM_Warehouse());
						
						List<LotMasterDto> lotMasterDtoList = lotMasterDao.lotMasterMatSelectDao(searchDto);
						
						double totalQty = list.getLM_ReturnQty();
						
						List<LotMasterDto> tempList = new ArrayList<LotMasterDto>();
						
						for(LotMasterDto LMDtoList : lotMasterDtoList) {							
							totalQty -= LMDtoList.getLM_Qty();
							
							if(totalQty <= 0){
								LMDtoList.setLM_ReturnQty((int) totalQty + LMDtoList.getLM_Qty());
								tempList.add(LMDtoList);
								break;
							}else {
								tempList.add(LMDtoList);
							}
						}
						
						for(LotMasterDto lotMasterDto : tempList) {
							
							String lotNo = lotMasterDto.getLM_LotNo();
							int no = lotTransDao.lotTransNoSelectDao2(lotNo);
							double qty = (-1) * lotMasterDto.getLM_ReturnQty();
							String itemCode = lotMasterDto.getLM_ItemCode();
							String before = "52";
							String after = "51";
							String classfy = "207";
							
							SalesInMatDto salesInMatDto = new SalesInMatDto();
							
							salesInMatDto.setSales_InMat_No(no);
							salesInMatDto.setSales_InMat_Lot_No(lotNo);
							salesInMatDto.setSales_InMat_Code(itemCode);
							salesInMatDto.setSales_InMat_Qty((int) qty);
							salesInMatDto.setSales_InMat_Rcv_Clsfc(classfy);
							salesInMatDto.setSales_InMat_Modifier(userCode);
							
							stockDao.stockUpdateDao(qty, itemCode, before);
							
							lotMasterDao.lotMasterUpdateDao(qty, lotNo, before);							
							
							salesInputDao.salesInMatInsertDao2(salesInMatDto);

							lotTransDao.lotTransInsertDao2(no, lotNo, itemCode, (-1)*qty, before, after, classfy);
							
							stockDao.stockUpdateDao((-1)*qty, itemCode, after);

							lotMasterDao.lotMasterUpdateDao((-1)*qty, lotNo, after);
						}
					}
				}
			});	
			return 1;			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 입고 반품 저장
	public int salesInReturnInsert(List<Sales_InMat_tbl> salesInReturnDtoList, String Modifier) {
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
					
					/*
					 * for (int i=0; i<salesInReturnDtoList.size(); i++) {
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
					 * 
					 * 
					 */
					
					for (int i=0; i<salesInReturnDtoList.size(); i++) {
						LM_LotNo = salesInReturnDtoList.get(i).getSales_InMat_Lot_No();
						LM_Qty = (double) salesInReturnDtoList.get(i).getSales_InMat_Qty();
						LT_Qty = (double) salesInReturnDtoList.get(i).getSales_InMat_Qty();
						Sales_InMat_Qty += salesInReturnDtoList.get(i).getSales_InMat_Qty();
						
						LM_ItemCode = salesInReturnDtoList.get(i).getSales_InMat_Code();
						LT_ItemCode = salesInReturnDtoList.get(i).getSales_InMat_Code();
						
						// lotMaster 소포장 생산창고 +
						lotMasterDao.lotMasterUpdateDao(LM_Qty, LM_LotNo, LT_Before);;
						// stock 소포장 반품수량 +
						stockDao.stockReturnUpdateDao(LM_Qty, LM_ItemCode, LT_Before);
						// sales_packing_tbl 상태 '입고반품'
						salesPackingDao.salesPackingUpdateDao(salesInReturnDtoList.get(i));
						LM_LotNo = salesInReturnDtoList.get(i).getSales_InMat_Lot_No();
						LT_LotNo = salesInReturnDtoList.get(i).getSales_InMat_Lot_No();
					
						LM_SumQty += LM_Qty;
						LT_SumQty += LT_Qty;
					}
					
					// 영업에는 소박스 제품 수량 합계가 들어가야하기 때문에 sum을 해준다.
					LM_Qty = LM_SumQty;
					LT_Qty = LT_SumQty;
					LT_Send_Clsfc = "207";
					
					// lotMaster 대포장 영업창고 -
					lotMasterDao.lotMasterUpdateDao((-1)*LM_Qty, LM_LotNo, LT_After);
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
					stockDao.stockUpdateDao(LM_Qty, LM_ItemCode, LT_After);
					
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
