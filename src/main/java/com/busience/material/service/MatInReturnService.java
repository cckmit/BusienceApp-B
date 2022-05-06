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
	
	//등록
	public List<InMatDto> matInReturnSelect(SearchDto searchDto){
		return inMatDao.inMatReturnSelectDao(searchDto);
	}
	
	//저장
	public int matInReturnSave(List<InMatDto> inMatDtoList, String userCode){

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<inMatDtoList.size();i++) {
						InMatDto inMatDto = inMatDtoList.get(i);

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
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
