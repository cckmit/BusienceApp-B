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
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dao.TransDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.StockDto;
import com.busience.material.dto.TransDto;

@Service
public class MatOutReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;

	@Autowired
	TransDao transDao;
	
	@Autowired
	OutMatDao outMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//재고이동 리스트 조회
	public List<LotMasterDto> stockTransSelect(SearchDto searchDto){
		return lotMasterDao.stockTransSelectDao(searchDto);
	}
	
	//등록
	public List<OutMatDto> matOutReturnSelect(SearchDto searchDto){
		return outMatDao.outMatReturnSelectDao(searchDto);
	}
	
	//저장
	public int matOutReturnSave(List<LotMasterDto> lotMasterDtoList, String userCode){
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					outReturnSave(lotMasterDtoList, userCode);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//저장
	public int matOutReturnLXSave(List<StockDto> StockDtoList, String userCode){
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<LotMasterDto> tempList = new ArrayList<LotMasterDto>();
					
					for(StockDto stockDto : StockDtoList) {
						SearchDto searchDto = new SearchDto();
						searchDto.setItemCode(stockDto.getS_ItemCode());
						searchDto.setWarehouse(stockDto.getS_Warehouse());
						
						List<LotMasterDto> lotMasterDtoList = lotMasterDao.lotMasterMatSelectDao(searchDto);
						System.out.println(stockDto);
						double totalQty = stockDto.getS_ReturnQty();
						for(LotMasterDto LMDtoList : lotMasterDtoList) {
							if(totalQty <= 0){
								break;
							}else if(totalQty >= LMDtoList.getLM_Qty()) {
								totalQty -= LMDtoList.getLM_Qty();
								tempList.add(LMDtoList);
							}else if(totalQty < LMDtoList.getLM_Qty()){
								LMDtoList.setLM_Qty((int) totalQty);
								tempList.add(LMDtoList);
							}
						}
						outReturnSave(tempList, userCode);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void outReturnSave(List<LotMasterDto> lotMasterDtoList, String userCode) {
		List<DtlDto> warehouseList = dtlDao.findByCode(10);
		String after = dtlDao.findByCode(10).get(0).getCHILD_TBL_NO();	
		String classfy = dtlDao.findByCode(18).get(2).getCHILD_TBL_NO();
		
		for(LotMasterDto lotMasterDto : lotMasterDtoList) {
			String lotNo = lotMasterDto.getLM_LotNo();
			int no = lotTransDao.lotTransNoSelectDao(lotNo);
			String itemCode = lotMasterDto.getLM_ItemCode();
			double qty = lotMasterDto.getLM_TransQty();
			String before = lotMasterDto.getLM_Warehouse();
			
			//랏마스터 업데이트
			lotMasterDao.salesLotMasterInsertUpdateDao(
					lotNo, itemCode, (-1)*qty, before
					);
			//재고 업데이트
			stockDao.stockInsertUpdateDao(itemCode, (-1)*qty, before);
			
			//트랜스
			transDao.transInsertDao(
					no, lotNo, itemCode, qty, before, after, classfy
					);
			
			//랏트랜스
			lotTransDao.lotTransInsertDao(
					no, lotNo, itemCode, qty, before, after, classfy
					);
			
			//랏마스터
			lotMasterDao.salesLotMasterInsertUpdateDao(
					lotNo, itemCode, qty, after
					);
			
			//재고
			stockDao.stockInsertUpdateDao( itemCode, qty, after);
			
			OutMatDto outMatDto = new OutMatDto();
			outMatDto.setOM_No(no);
			outMatDto.setOM_RequestNo("12-000000-00");
			outMatDto.setOM_LotNo(lotNo);
			outMatDto.setOM_ItemCode(itemCode);
			outMatDto.setOM_DeptCode("12");
			outMatDto.setOM_Qty(-1*qty);
			outMatDto.setOM_WareHouse(warehouseList.get(0).getCHILD_TBL_TYPE());
			outMatDto.setOM_Send_Clsfc(classfy);
			outMatDto.setOM_Modifier(userCode);	
			
			//출고
			outMatDao.outMatInsertDao(outMatDto);
		}
	}
	
	//등록
	public List<TransDto> transSelect(SearchDto searchDto){
		return transDao.transSelectDao(searchDto);
	}
}
