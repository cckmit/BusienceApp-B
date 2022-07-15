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

						int no = inMatDto.getInMat_No();
						String lotNo = inMatDto.getInMat_Lot_No();
						String itemCode = inMatDto.getInMat_Code();
						int qty = inMatDto.getInMat_Qty();
						String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
						String before = "";
						String after = WarehouseList.get(0).getCHILD_TBL_NO();
						String classfy = inMatDto.getInMat_Rcv_Clsfc();
						
						ItemDto itemDto = itemDao.selectItemCode(itemCode);
						
						inMatDtoList.get(i).setInMat_STND_1(itemDto.getPRODUCT_INFO_STND_1());
						inMatDtoList.get(i).setInMat_STND_2(itemDto.getPRODUCT_INFO_STND_2());
						inMatDtoList.get(i).setInMat_Client_Name(inMatDto.getInMat_Client_Name());
						
						
						for(int j=0;j<qty;j++) {
							//랏번호 생성
							lotNo = lotNoDao.rawlotNoSelectDao(inMatDto.getInMat_Date(), itemCode);
							inMatDto.setInMat_Lot_No(lotNo);
							
							double realQty = 1;
							inMatDto.setInMat_Qty((int) realQty);
							
							// 자재창고에 저장
							inMatDto.setInMat_Warehouse(Warehouse);
							// 랏트랜스번호 가져오기
							inMatDto.setInMat_No(lotTransDao.lotTransNoSelectDao(lotNo));
							// 이동 설정하기 외부 -> 자재창고
							inMatDto.setInMat_Before(before);
							inMatDto.setInMat_After(after);
							// 작업자
							inMatDto.setInMat_Modifier(userCode);

							// 랏마스터 저장
							lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, realQty, Warehouse);

							// 재고 저장
							stockDao.stockInsertUpdateDao(itemCode, realQty, Warehouse);

							// 자재입고 저장
							inMatDao.inMatInsertDao(inMatDto);

							// 랏트랜스 저장
							lotTransDao.lotTransInsertDao(no, lotNo, itemCode, realQty, before, after, classfy);

							// 발주리스트 저장
							orderListDao.orderListUpdateDao(inMatDto);

							// 발주마스터 저장
							orderMasterDao.orderMasterUpdateDao(inMatDto);
							
							//저장한 리스트 생성
							LabelPrintDtoList.add(labelPrintDao.rawMaterialLabelSelectDao(lotNo));
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
