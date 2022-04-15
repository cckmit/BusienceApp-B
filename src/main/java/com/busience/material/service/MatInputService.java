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
	public int matInputRegister(List<InMatDto> InMatDtoList, String userCode){
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<InMatDtoList.size();i++) {
						InMatDto inMatDto = InMatDtoList.get(i);
						//작업자
						inMatDto.setInMat_Modifier(userCode);
						
						//랏번호가 없을경우 랏번호 생성
						if(inMatDto.getInMat_Lot_No() == null || inMatDto.getInMat_Lot_No().isBlank()) {
							String LotNo = lotNoDao.lotNoSelectDao(inMatDto);
							inMatDto.setInMat_Lot_No(LotNo);

							//랏번호
							lotNoDao.lotNoMatUpdateDao();
						}
						
						//랏트랜스번호 가져오기
						int LotTransNo = lotTransDao.lotTransNoSelectDao(inMatDto);
						inMatDto.setInMat_No(LotTransNo);
						
						//이동 설정하기 외부 -> 자재창고
						inMatDto.setInMat_Before("");
						inMatDto.setInMat_After(wareHouseList.get(0).getCHILD_TBL_NO());
						
						inMatDto.setInMat_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());

						//랏마스터
						lotMasterDao.lotMasterInsertUpdateDao(inMatDto);
						
						//랏트랜스
						lotTransDao.lotTransInsertDao(inMatDto);
						
						//자재입고
						inMatDao.inMatInsertDao(inMatDto);
						
						//재고
						stockDao.stockInsertUpdateDao(inMatDto);
						
						//발주리스트
						orderListDao.orderListUpdateDao(inMatDto);
						
						//발주마스터
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
