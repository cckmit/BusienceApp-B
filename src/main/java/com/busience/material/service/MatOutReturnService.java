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
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.OutMatDto;

@Service
public class MatOutReturnService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	OutMatDao outMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//등록
	public List<OutMatDto> matOutReturnSelect(SearchDto searchDto){
		return outMatDao.outMatReturnSelectDao(searchDto);
	}
	
	//저장
	public int matOutReturnSave(List<OutMatDto> outMatDtoList, String userCode){
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> wareHouseList = dtlDao.findByCode(10);
					
					for(int i=0;i<outMatDtoList.size();i++) {
						OutMatDto outMatDto = outMatDtoList.get(i);
						
						String lotNo = outMatDto.getOM_LotNo();
						int no = lotTransDao.lotTransNoSelectDao(lotNo);
						String itemCode = outMatDto.getOM_ItemCode();
						double qty = outMatDto.getOM_ReturnQty();
						String wareHouse = wareHouseList.get(1).getCHILD_TBL_NO();
						String before = wareHouseList.get(1).getCHILD_TBL_NO();
						String after = wareHouseList.get(0).getCHILD_TBL_NO();
						String classfy = "210";
						
						outMatDto.setOM_WareHouse(wareHouse);
						outMatDto.setOM_Send_Clsfc(classfy);
						outMatDto.setOM_Modifier(userCode);

						//랏트랜스번호 가져오기
						outMatDto.setOM_No(no);
						
						//랏마스터 업데이트
						lotMasterDao.salesLotMasterInsertUpdateDao(
								lotNo, itemCode, (-1)*qty, wareHouse
								);
						//재고 업데이트
						stockDao.stockInsertUpdateDao(itemCode, (-1)*qty, before);

						//수량 음수처리
						outMatDto.setOM_Qty(-1*outMatDto.getOM_Qty());

						//출고
						outMatDao.outMatInsertDao(outMatDto);
																					
						//랏트랜스
						lotTransDao.lotTransInsertDao(
								no, lotNo, itemCode, qty, before, after, classfy
								);

						//랏마스터
						lotMasterDao.salesLotMasterInsertUpdateDao(
								lotNo, itemCode, qty, wareHouse
								);
						
						//재고
						stockDao.stockInsertUpdateDao( itemCode, qty, after);
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
