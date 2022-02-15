package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dao.ProductionDao;
import com.busience.common.dao.TestCheckDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.ProductionDto;
import com.busience.common.dto.TestCheckDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;

@Service
public class ProductionService {

	@Autowired
	ProductionDao productionDao;
	
	@Autowired
	TestCheckDao testCheckDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	boolean pause = true;
	
	public boolean pauseChange(boolean TF) {
		pause = TF;
		return pause;
	}
	
	//생산량 저장
	public int insertProduction(String equip, int value) {
		//pause가 false일 경우 일시중지
		if(!pause) {
			return 0;
		}
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//작업지시 옵션 검색
					List<DtlDto> dtlDtoList = dtlDao.findAllByCode(38);
					//테스트 체크용
					TestCheckDto testCheckDto = new TestCheckDto();
					testCheckDto.setIequip(equip);
					testCheckDto.setIvalue(value);
					testCheckDao.TestInsertDao(testCheckDto);
					
					List<WorkOrderDto> workOrderDtoList = productionDao.selectWorkOrderDao(equip);	
					
					//작업지시 있을때 맞춰서 인서트, 해당설비의 작업시작값이 꼭 1개
					if(workOrderDtoList.size()>0) {
						//해당 품목의 정보를 가져옴
						ItemDto itemDto = itemDao.selectItemCode(workOrderDtoList.get(0).getWorkOrder_ItemCode());	
												
						ProductionDto productionDto = new ProductionDto();
						productionDto.setPRODUCTION_WorkOrder_ONo(workOrderDtoList.get(0).getWorkOrder_ONo());
						productionDto.setPRODUCTION_Product_Code(workOrderDtoList.get(0).getWorkOrder_ItemCode());
						productionDto.setPRODUCTION_Equipment_Code(equip);
						
						//생산량 배수가 있으면 배수만큼 곱해줌 만약 0이거나 값이 없으면 들어온값 그대로
						if(itemDto.getPRODUCT_MULTIPLE()>0) {
							productionDto.setPRODUCTION_Volume(value*itemDto.getPRODUCT_MULTIPLE());
						}else {
							productionDto.setPRODUCTION_Volume(value);
						}
						//PRODUCTION_MGMT_TBL2에 인서트
						productionDao.insertProductionDao(productionDto);
						
						//작업지시 옵션
						productionDto.setWorkOrder_Status(dtlDtoList.get(2).getCHILD_TBL_USE_STATUS());
						//작업지시테이블 업데이트
						productionDao.updateWorkOrderDao(productionDto);
					}
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	//work order 조회
	public List<WorkOrderDto> getWorkOrder(String code) {
    	return productionDao.selectWorkOrderDao(code);
	}
}
