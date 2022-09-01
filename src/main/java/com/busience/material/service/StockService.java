package com.busience.material.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.RequestMasterDao;
import com.busience.material.dao.RequestSubDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockDto;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dto.LabelPrintDto;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dao.UserDao;
import com.busience.standard.dto.ItemDto;

@Service
public class StockService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	ItemDao itemDao;

	@Autowired
	StockDao stockDao;

	@Autowired
	LotMasterDao lotMasterDao;

	@Autowired
	LotTransDao lotTransDao;

	@Autowired
	LotNoDao lotNoDao;

	@Autowired
	OutMatDao outMatDao;

	@Autowired
	UserDao userDao;

	@Autowired
	RequestMasterDao requestMasterDao;

	@Autowired
	RequestSubDao requestSubDao;
	
	@Autowired
	LabelPrintDao labelPrintDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	// 재고테이블 조회
	public List<StockDto> stockSelect(SearchDto searchDto) {
		return stockDao.stockSelectDao(searchDto);
	}

	// 재고 Lot-품목 조회
	public List<LotMasterDto> stockLotSelect(SearchDto searchDto) {
		return lotMasterDao.lotMasterMaterialSelectDao(searchDto);
	}

	// 재고 조정
	public List<LotMasterDto> StockChanageSelect(SearchDto searchDto) {
		return lotMasterDao.lotMasterSelectDao(searchDto);
	}

	// 영업 재고테이블 조회
	public List<StockDto> salesStockSelectDao(SearchDto searchDto) {

		List<StockDto> salesStockList = stockDao.salesStockSelectDao(searchDto);

		for (StockDto dto : salesStockList) {
			if (dto.getS_ItemCode() == null || dto.getS_ItemCode() == "") {
				dto.setS_ItemCode("Grand Total");
				dto.setS_ItemName("");
				dto.setS_Item_Standard_1("");
				dto.setS_Item_Standard_2("");
				dto.setS_Item_Classfy_1_Name("");
				dto.setS_Item_Classfy_2_Name("");
				dto.setS_Item_Material("");
				dto.setS_Item_Unit("");
			}
		}
		return salesStockList;

	}

	// 영업 재고 Lot-품목 조회
	public List<StockDto> salesStockLotSelectDao(SearchDto searchDto) {
		return stockDao.salesStockLotSelectDao(searchDto);
	}

	// 출고지시수량 재고 확인
	public List<StockDto> salesOutputStockDao(SearchDto searchDto) {
		return stockDao.salesOutputStockDao(searchDto);
	}

	// 출고지시조회 재고 확인
	public List<StockDto> salesOutputOrderStockDao(SearchDto searchDto) {
		return stockDao.salesOutputOrderStockDao(searchDto);
	}

	// 재고 조정 저장
	public List<LabelPrintDto> StockChangeSave(List<LotMasterDto> stockDtoList, String warehouse, String Modifier) {

		try {
			List<LabelPrintDto> labelPrintList = new ArrayList<LabelPrintDto>();
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					String deptCode = dtlDao.findByCode(3).get(0).getCHILD_TBL_NO();
					String classfy = dtlDao.findByCode(18).get(4).getCHILD_TBL_NO();
					String rawMaterial = dtlDao.findByCode(5).get(0).getCHILD_TBL_NO();
					String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					String before = warehouse;
					String after = warehouse;
					
					//자재 창고에서 새로 생성될때
					for(int i=0;i<stockDtoList.size();i++) {
						
						String itemCode = stockDtoList.get(i).getLM_ItemCode();
						double changeQty = stockDtoList.get(i).getLM_ChangeQty();
						double qty = stockDtoList.get(i).getLM_Qty();
						double gapQty = changeQty - qty;
						
						ItemDto itemDto = itemDao.selectItemCode(itemCode);
						
						//원자재이면서 재고가 증가할경우 개당랏생성
						if(gapQty > 0 && rawMaterial.equals(itemDto.getPRODUCT_MTRL_CLSFC())) {
							for(int j=0;j<gapQty;j++) {
								double unitQty = 1; 
								String lotNo = lotNoDao.rawlotNoSelectDao(date ,itemCode);
								int no = lotTransDao.lotTransNoSelectDao(lotNo);
								
								OutMatDto outMatDto = new OutMatDto();
								outMatDto.setOM_LotNo(lotNo);
								outMatDto.setOM_RequestNo(deptCode+"-000000-00");
								outMatDto.setOM_DeptCode(deptCode);
								outMatDto.setOM_ItemCode(itemCode);
								outMatDto.setOM_Qty((-1)*unitQty);
								outMatDto.setOM_Before(before);
								outMatDto.setOM_After(after);
								outMatDto.setOM_Send_Clsfc(classfy);
								outMatDto.setOM_Modifier(Modifier);
								
								//랏마스터
								lotMasterDao.salesLotMasterInsertUpdateDao(
										lotNo, itemCode, unitQty, after
										);
								
								//랏트랜스
								lotTransDao.lotTransInsertDao(
										no, lotNo, itemCode, unitQty, before, after, classfy
										);
								
								//재고
								stockDao.stockInsertUpdateDao(itemCode, unitQty, after);
								
								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);
								
								labelPrintList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo, warehouse));
							}
						}
						else {
							String lotNo = stockDtoList.get(i).getLM_LotNo();
							if(stockDtoList.get(i).getLM_LotNo().length()==0) {
								lotNo = lotNoDao.rawlotNoSelectDao(date ,itemCode);
							}
							int no = lotTransDao.lotTransNoSelectDao(lotNo);
							
							OutMatDto outMatDto = new OutMatDto();
							outMatDto.setOM_LotNo(lotNo);
							outMatDto.setOM_RequestNo(deptCode+"-000000-00");
							outMatDto.setOM_DeptCode(deptCode);
							outMatDto.setOM_ItemCode(itemCode);
							outMatDto.setOM_Qty((-1)*gapQty);
							outMatDto.setOM_Before(before);
							outMatDto.setOM_After(after);
							outMatDto.setOM_Send_Clsfc(classfy);
							outMatDto.setOM_Modifier(Modifier);
							
							//랏마스터
							lotMasterDao.salesLotMasterInsertUpdateDao(
									lotNo, itemCode, gapQty, after
									);
							
							//랏트랜스
							lotTransDao.lotTransInsertDao(
									no, lotNo, itemCode, gapQty, before, after, classfy
									);
							
							//재고
							stockDao.stockInsertUpdateDao(itemCode, gapQty, after);
							
							// 자재출고 저장
							outMatDao.outMatInsertDao(outMatDto);
							
							if(gapQty > 0) {
								labelPrintList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo, warehouse));
							}
						}
					}
					/*
					for (int i = 0; i < stockDtoList.size(); i++) {
						StockDto stockDto = stockDtoList.get(i);
						OutMatDto outMatDto = new OutMatDto();
						UserDto userDto = userDao.selectUser(Modifier);

						outMatDto.setOM_Modifier(Modifier);
						outMatDto.setOM_ItemCode(stockDtoList.get(i).getS_ItemCode());

						int no = i;
						String lotNo = stockDto.getS_LotNo();
						String itemCode = stockDto.getS_ItemCode();
						Double changeqty;
						Double qty;
						Double outMatQty;

						String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
						String before = Warehouse;
						String after = Warehouse;

						String classfy = "345";

						changeqty = (Double) stockDto.getS_ChangeQty();
						outMatQty = (Double) stockDto.getS_Qty() - (Double) stockDto.getS_ChangeQty();

						outMatDto.setOM_RequestNo("99-221231-01");

						outMatDto.setOM_DeptCode(userDto.getDept_Code());

						outMatDto.setOM_Before(Warehouse);

						outMatDto.setOM_After(Warehouse);

						outMatDto.setOM_Send_Clsfc("344");

						no = lotTransDao.lotTransNoSelectDao(lotNo);

						// B, C 일 때
						if (itemCode.charAt(0) == 'B' || itemCode.charAt(0) == 'C') {
							// 랏번호가 없을경우 랏번호 생성

							if (lotNo == null || lotNo.isBlank()) {
								Date Date = new Date();
								SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
							    String date = sdformat.format(Date);
							    
								lotNo = lotNoDao.rawlotNoSelectDao((String)date ,outMatDto.getOM_ItemCode());
								outMatDto.setOM_LotNo(lotNo);

								qty = changeqty;

								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

								// 랏트랜스번호 가져오기
								outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

								outMatDto.setOM_LotNo(lotNo);

								qty = changeqty;

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

								qty = outMatQty;

								outMatDto.setOM_Qty(qty);

								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);

								Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

								outMatQty = (double) stockQty;

								stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);

							} else if (stockDto.getS_Qty() > stockDto.getS_ChangeQty()) {

								qty = (-1) * outMatQty;

								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

								// 랏트랜스번호 가져오기
								outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

								outMatDto.setOM_LotNo(lotNo);

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

								qty = outMatQty;

								outMatDto.setOM_Qty(qty);

								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);

								Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

								outMatQty = (double) stockQty;

								stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);
																
							} else if (stockDto.getS_Qty() < stockDto.getS_ChangeQty()) {

								qty = (-1) * outMatQty;

								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

								// 랏트랜스번호 가져오기
								outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

								outMatDto.setOM_LotNo(lotNo);

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

								qty = outMatQty;

								outMatDto.setOM_Qty(qty);

								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);

								Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

								outMatQty = (double) stockQty;

								stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);
								
							}

						} else if (itemCode.charAt(0) == 'A') {

							if (lotNo == null || lotNo.isBlank()) {
								
								for (int k = 0; k < stockDto.getS_ChangeQty(); k++) {
									Date Date = new Date();
									SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
								    String date = sdformat.format(Date);
								    
									lotNo = lotNoDao.rawlotNoSelectDao((String)date ,outMatDto.getOM_ItemCode());
									outMatDto.setOM_LotNo(lotNo);

									// 랏번호 업데이트
									lotNoDao.lotNoMatUpdateDao();

									qty = 1.0;

									// 랏마스터 저장
									lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

									// 랏트랜스번호 가져오기
									outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

									outMatDto.setOM_LotNo(lotNo);

									qty = 1.0;

									// 랏트랜스 저장
									lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

									qty = 1.0;

									outMatDto.setOM_Qty(qty);

									// 자재출고 저장
									outMatDao.outMatInsertDao(outMatDto);

									Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

									outMatQty = (double) stockQty;

									stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);
									
								}

							} else if (stockDto.getS_Qty() > stockDto.getS_ChangeQty()) {

								qty = (-1)*stockDto.getS_Qty();

								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

								// 랏트랜스번호 가져오기
								outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

								outMatDto.setOM_LotNo(lotNo);

								qty = (-1) * outMatQty;

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

								qty = outMatQty;

								outMatDto.setOM_Qty(qty);

								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);

								Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

								outMatQty = (double) stockQty;

								stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);
								
							} else if (stockDto.getS_Qty() < stockDto.getS_ChangeQty()) {

								qty = stockDto.getS_ChangeQty();

								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

								// 랏트랜스번호 가져오기
								outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

								outMatDto.setOM_LotNo(lotNo);

								qty = (-1) * outMatQty;

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

								qty = outMatQty;

								outMatDto.setOM_Qty(qty);

								// 자재출고 저장
								outMatDao.outMatInsertDao(outMatDto);

								Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

								outMatQty = (double) stockQty;

								stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);
								
							}
							
						} 
						labelPrintList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo, Warehouse));
					}*/
				}
			});
			return labelPrintList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// return stockDao.stockChangeSave(stockDto);
	}

}
