package com.busience.material.service;

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
import com.busience.material.dto.InMatDto;

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
	StockDao stockDao;
	
	@Autowired
	OrderMasterDao orderMasterDao;
	
	@Autowired
	OrderListDao orderListDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public int matInputRegister(List<InMatDto> inMatDtoList, String userCode){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<inMatDtoList.size();i++) {
						InMatDto inMatDto = inMatDtoList.get(i);
						
						int no = inMatDto.getInMat_No();
						String lotNo = inMatDto.getInMat_Lot_No();
						String itemCode = inMatDto.getInMat_Code();
						double qty = (double) inMatDto.getInMat_Qty();
						String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
						String before = "";
						String after = WarehouseList.get(0).getCHILD_TBL_NO();
						String classfy = inMatDto.getInMat_Rcv_Clsfc();
						
						//랏번호가 없을경우 랏번호 생성
						if(lotNo == null || lotNo.isBlank()) {
							lotNo = lotNoDao.lotNoSelectDao(inMatDto);
							inMatDto.setInMat_Lot_No(lotNo);

							//랏번호 업데이트
							lotNoDao.lotNoMatUpdateDao();
						}
						
						//자재창고에 저장
						inMatDto.setInMat_Warehouse(Warehouse);						
						//랏트랜스번호 가져오기
						inMatDto.setInMat_No(lotTransDao.lotTransNoSelectDao(lotNo));						
						//이동 설정하기 외부 -> 자재창고
						inMatDto.setInMat_Before(before);
						inMatDto.setInMat_After(after);						
						//작업자
						inMatDto.setInMat_Modifier(userCode);
						
						//랏마스터 저장
						lotMasterDao.lotMasterInsertUpdateDao(
								lotNo, itemCode, qty, Warehouse
								);
						
						//재고 저장
						stockDao.stockInsertUpdateDao(
								itemCode, qty, Warehouse
								);
						
						//자재입고 저장
						inMatDao.inMatInsertDao(inMatDto);
						
						//랏트랜스 저장
						lotTransDao.lotTransInsertDao(
								no, lotNo, itemCode, qty, before, after, classfy
								);
												
						//발주리스트 저장
						orderListDao.orderListUpdateDao(inMatDto);
						
						//발주마스터 저장
						orderMasterDao.orderMasterUpdateDao(inMatDto);
					}
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//입고조회
	public List<InMatDto> matInputList(SearchDto searchDto) {
		return inMatDao.inMatListDao(searchDto);
	}
	
	//입고 조건별 조회
	public List<InMatDto> matInputOtherList(SearchDto searchDto) {
		List<InMatDto> inMatDtoList = inMatDao.inMatOtherListDao(searchDto);
		for(int i=0;i<inMatDtoList.size();i++) {
			String itemCode = inMatDtoList.get(i).getInMat_Code();
			String clientCode = inMatDtoList.get(i).getInMat_Client_Code();
			
			if(itemCode == null || clientCode == null) {
				inMatDtoList.get(i).setInMat_Order_No("");
				inMatDtoList.get(i).setInMat_Lot_No("Sub Total");
				inMatDtoList.get(i).setInMat_Date("");
				inMatDtoList.get(i).setInMat_Rcv_Clsfc_Name("");
			}
			if(itemCode == null) {
				inMatDtoList.get(i).setInMat_STND_1("");
				inMatDtoList.get(i).setInMat_STND_2("");
				inMatDtoList.get(i).setInMat_UNIT("");
				inMatDtoList.get(i).setInMat_Unit_Price(0);
				inMatDtoList.get(i).setInMat_Name("");
				
			}else if(clientCode == null) {
				inMatDtoList.get(i).setInMat_Client_Name("");
			}
			if(itemCode == null && clientCode == null) {
				inMatDtoList.get(i).setInMat_Lot_No("Grand Total");
			}
		}
		return inMatDtoList;
	}
	
	//납품 명세서 거래처 리스트
	public List<InMatDto> matInputDeliveryMaster(SearchDto searchDto) {
		return inMatDao.inMatDeliveryMasterDao(searchDto);
	}
	
	//납품 명세서 거래처 리스트
	public List<InMatDto> matInputDeliverySub(SearchDto searchDto) {
		return inMatDao.inMatDeliverySubDao(searchDto);
	}
}
