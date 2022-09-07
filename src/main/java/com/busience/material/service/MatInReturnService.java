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
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.InMatDao;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.InMatDto;

@Service
public class MatInReturnService {

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
	TransactionTemplate transactionTemplate;
	
	public List<InMatDto> matInReturnSelect(SearchDto searchDto){
		return inMatDao.inMatReturnSelectDao(searchDto);
	}
	
	public List<InMatDto> matInReturnLXSelect(SearchDto searchDto){
		return inMatDao.inMatReturnLXSelectDao(searchDto);
	}
	
	//저장
	public int matInReturnSave(List<InMatDto> inMatDtoList, String userCode){

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					inReturnSave(inMatDtoList, userCode);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int matInReturnLXSave(List<InMatDto> inMatDtoList, String userCode){

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					for(InMatDto baseInMat : inMatDtoList) {
						List<InMatDto> tempList = new ArrayList<InMatDto>();
						
						List<InMatDto> inMatLotList = inMatDao.inReturnSelectDao(baseInMat.getInMat_Order_No(), baseInMat.getInMat_Code());

						double totalQty = baseInMat.getInReturn_Qty();
						for(InMatDto inMatDto : inMatLotList) {
							inMatDto.setInMat_Date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

							totalQty -= inMatDto.getInReturn_Qty();
							
							if(totalQty <= 0) {
								inMatDto.setInReturn_Qty((int) totalQty + inMatDto.getInReturn_Qty());
								tempList.add(inMatDto);
								break;
							}else {
								tempList.add(inMatDto);								
							}
						}
						inReturnSave(tempList, userCode);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void inReturnSave(List<InMatDto> inMatDtoList, String userCode) {
		List<DtlDto> WarehouseList = dtlDao.findByCode(10);

		for(InMatDto inMatDto : inMatDtoList) {
			
			String lotNo = inMatDto.getInMat_Lot_No();
			int no = lotTransDao.lotTransNoSelectDao(lotNo);
			String itemCode = inMatDto.getInMat_Code();
			double qty = (double) inMatDto.getInReturn_Qty();
			String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
			String before = WarehouseList.get(0).getCHILD_TBL_NO();
			String after = "";
			String classfy = "207";
			
			//자재창고에 저장
			inMatDto.setInMat_Warehouse(Warehouse);						
			//랏트랜스번호 가져오기
			inMatDto.setInMat_No(no);
			//이동 설정하기 외부 -> 자재창고
			inMatDto.setInMat_Before(before);
			inMatDto.setInMat_After(after);						
			//작업자
			inMatDto.setInMat_Modifier(userCode);
															
			//랏트랜스번호 가져오기
			inMatDto.setInMat_No(lotTransDao.lotTransNoSelectDao(inMatDto.getInMat_Lot_No()));
			
			//이동 설정하기
			inMatDto.setInMat_Before(WarehouseList.get(0).getCHILD_TBL_NO());
			inMatDto.setInMat_After("");
			
			inMatDto.setInMat_Warehouse(WarehouseList.get(0).getCHILD_TBL_NO());
			
			//입고 반품
			inMatDto.setInMat_Rcv_Clsfc(classfy);
			
			//랏마스터 저장
			lotMasterDao.lotMasterInsertUpdateDao(
					lotNo, itemCode, -1*qty, Warehouse
					);
			
			//재고 저장
			stockDao.stockInsertDao(
					itemCode, -1*qty, Warehouse
					);
			
			//입고 반품 처리는 -수량
			inMatDto.setInMat_Qty((-1)*((int) qty)); 
			
			//자재입고 저장
			inMatDao.inMatInsertDao(inMatDto);
			
			//랏트랜스 저장
			lotTransDao.lotTransInsertDao(
					no, lotNo, itemCode, qty, before, after, classfy
					);
		}
	}
}
