package com.busience.material.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.InMatDao;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OrderListDao;
import com.busience.material.dao.OrderMasterDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dao.TemporaryStorageDao;
import com.busience.material.dto.InMatDto;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dto.LabelPrintDto;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;

@Service
public class MatInputService {

	@Autowired
	DtlDao dtlDao;

	@Autowired
	LotMasterDao lotMasterDao;

	@Autowired
	LotTransDao lotTransDao;

	@Autowired
	LotNoDao lotNoDao;

	@Autowired
	InMatDao inMatDao;

	@Autowired
	TemporaryStorageDao temporaryStorageDao;

	@Autowired
	StockDao stockDao;
	
	@Autowired
	ItemDao itemDao;

	@Autowired
	OrderMasterDao orderMasterDao;

	@Autowired
	OrderListDao orderListDao;
	
	@Autowired
	LabelPrintDao labelPrintDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	// 등록
	public List<LabelPrintDto> matInputRegister(List<InMatDto> inMatDtoList, String userCode) {
		try {
			List<LabelPrintDto> LabelPrintDtoList = new ArrayList<LabelPrintDto>();
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);

					for (int i = 0; i < inMatDtoList.size(); i++) {
						InMatDto inMatDto = inMatDtoList.get(i);
						inMatDto.setInMat_Modifier(userCode);
												
						String orderNo = inMatDto.getInMat_Order_No();
						int no = inMatDto.getInMat_No();
						String lotNo = inMatDto.getInMat_Lot_No();
						String itemCode = inMatDto.getInMat_Code();
						int qty = inMatDto.getInMat_Qty();
						int unitPrice = inMatDto.getInMat_Unit_Price();
						String clientCode = inMatDto.getInMat_Client_Code();
						String inMatDate = inMatDto.getInMat_Date();
						String warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
						String before = "";
						String after = warehouse;
						String classfy = inMatDto.getInMat_Rcv_Clsfc();
						
						ItemDto itemDto = itemDao.selectItemCode(itemCode);
						
						inMatDtoList.get(i).setInMat_STND_1(itemDto.getPRODUCT_INFO_STND_1());
						inMatDtoList.get(i).setInMat_STND_2(itemDto.getPRODUCT_INFO_STND_2());
						inMatDtoList.get(i).setInMat_Client_Name(inMatDto.getInMat_Client_Name());

						// 자재창고에 저장
						inMatDto.setInMat_Warehouse(warehouse);
						// 이동 설정하기 외부 -> 자재창고
						inMatDto.setInMat_Before(before);
						inMatDto.setInMat_After(after);
						// 작업자
						inMatDto.setInMat_Modifier(userCode);

						// 재고 저장
						stockDao.stockInsertUpdateDao(itemCode,(double) qty, warehouse);

						// 발주리스트 저장
						orderListDao.orderListUpdateDao(inMatDto);

						// 발주마스터 저장
						orderMasterDao.orderMasterUpdateDao(inMatDto);
						
						
						int divideNum = 1;
						if(dtlDao.findAllByCode(31).get(3).getCHILD_TBL_USE_STATUS().equals("true")) {
							divideNum = qty;
						}
						
						if("24".equals(itemDto.getPRODUCT_MTRL_CLSFC())) {
							for(int j=0;j<divideNum;j++) {
								//랏번호 생성
								lotNo = lotNoDao.rawlotNoSelectDao(inMatDate, itemCode);
								inMatDto.setInMat_Lot_No(lotNo);
								
								double realQty = qty/divideNum;
								
								// 랏트랜스번호 가져오기
								no = lotTransDao.lotTransNoSelectDao(lotNo);
								
								// 랏마스터 저장
								lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, realQty, warehouse);
								
								// 자재입고 저장
								inMatDao.inMatInsert2Dao(orderNo, lotNo, itemCode, realQty, unitPrice, realQty*unitPrice, clientCode, inMatDate, classfy, userCode);

								// 랏트랜스 저장
								lotTransDao.lotTransInsertDao(no, lotNo, itemCode, realQty, before, after, classfy);

								//저장한 리스트 생성
								LabelPrintDtoList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo, warehouse));
							}	
						}else {
							//랏번호 생성
							lotNo = lotNoDao.rawlotNoSelectDao(inMatDate, itemCode);
							inMatDto.setInMat_Lot_No(lotNo);
							
							// 랏트랜스번호 가져오기
							no = lotTransDao.lotTransNoSelectDao(lotNo);
							
							// 랏마스터 저장
							lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, (double) qty, warehouse);
							
							// 자재입고 저장
							inMatDao.inMatInsert2Dao(orderNo, lotNo, itemCode, qty, unitPrice, qty*unitPrice, clientCode, inMatDate, classfy, userCode);

							// 랏트랜스 저장
							lotTransDao.lotTransInsertDao(no, lotNo, itemCode, (double) qty, before, after, classfy);

							//저장한 리스트 생성
							LabelPrintDtoList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo, warehouse));
						}
					}
				}
			});
			
			return LabelPrintDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 입고조회
	public List<InMatDto> matInputList(SearchDto searchDto) {
		return inMatDao.inMatListDao(searchDto);
	}

	// 입고 조건별 조회
	public List<InMatDto> matInputOtherList(SearchDto searchDto) {
		List<InMatDto> inMatDtoList = inMatDao.inMatOtherListDao(searchDto);
		for (int i = 0; i < inMatDtoList.size(); i++) {
			String itemCode = inMatDtoList.get(i).getInMat_Code();
			String clientCode = inMatDtoList.get(i).getInMat_Client_Code();

			if (itemCode == null || clientCode == null) {
				inMatDtoList.get(i).setInMat_Order_No("Sub Total");
				inMatDtoList.get(i).setInMat_Date("");
				inMatDtoList.get(i).setInMat_Rcv_Clsfc_Name("");
			}
			if (itemCode == null) {
				inMatDtoList.get(i).setInMat_STND_1("");
				inMatDtoList.get(i).setInMat_STND_2("");
				inMatDtoList.get(i).setInMat_UNIT("");
				inMatDtoList.get(i).setInMat_Unit_Price(0);
				inMatDtoList.get(i).setInMat_Name("");

			} else if (clientCode == null) {
				inMatDtoList.get(i).setInMat_Client_Name("");
			}
			if (itemCode == null && clientCode == null) {
				inMatDtoList.get(i).setInMat_Order_No("Grand Total");
			}
		}
		return inMatDtoList;
	}

	// 납품 명세서 거래처 리스트
	public List<InMatDto> matInputDeliveryMaster(SearchDto searchDto) {
		return inMatDao.inMatDeliveryMasterDao(searchDto);
	}

	// 납품 명세서 거래처 리스트
	public List<InMatDto> matInputDeliverySub(SearchDto searchDto) {
		return inMatDao.inMatDeliverySubDao(searchDto);
	}

}
