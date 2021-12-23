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

@Service
public class ProductionService {

	@Autowired
	ProductionDao productionDao;
	
	@Autowired
	TestCheckDao testCheckDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//가입시 메뉴 저장
	public int insertProduction(String equip, int value) {
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
						ProductionDto productionDto = new ProductionDto();
						productionDto.setPRODUCTION_WorkOrder_ONo(workOrderDtoList.get(0).getWorkOrder_ONo());
						productionDto.setPRODUCTION_Product_Code(workOrderDtoList.get(0).getWorkOrder_ItemCode());
						productionDto.setPRODUCTION_Equipment_Code(equip);
						productionDto.setPRODUCTION_Volume(value);
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
