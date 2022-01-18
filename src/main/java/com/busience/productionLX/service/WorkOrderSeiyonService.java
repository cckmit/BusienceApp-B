package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dao.WorkOrderSeiyonDao;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class WorkOrderSeiyonService {
	
	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	WorkOrderSeiyonDao workOrderSeiyonDao;
	@Autowired
	DtlDao dtlDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//작업지시 조회
	public List<WorkOrderDto> workOrderSelect(SearchDto searchDto) {
		return workOrderDao.workOrderSelectDao(searchDto);
	}

	public int WorkOrderSeiyonUpdate(WorkOrderDto workOrderDto) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
										
					List<DtlDto> dtlList = dtlDao.findByCode(29);
					
					SearchDto searchDto = new SearchDto();
					searchDto.setWorkOrderONo(workOrderDto.getWorkOrder_ONo());
					searchDto.setMachineCode(workOrderDto.getWorkOrder_EquipCode());
					
					//작업지시 상태
					//Y : 접수완료, S : 작업시작, E : 작업완료
					for(int j=0;j<dtlList.size();j++) {
						if(dtlList.get(j).getCHILD_TBL_RMARK().equals(workOrderDto.getWorkOrder_WorkStatus_Name())) {
							workOrderDto.setWorkOrder_WorkStatus(dtlList.get(j).getCHILD_TBL_NO());
						}
					}
					
					//상태가 작업완료로 바껴야 하는 경우
					if(workOrderDto.getWorkOrder_WorkStatus_Name().equals("E")){
												
						//설비1과 설비2에 일단 둘다 반영시킴
						int productQty = workOrderDao.workOrderSumQtyDao(searchDto);
						//P는 목표수량 R은 설비수량
						//목표수량(설비1수량) - 가져온수량(설비2수량) 이 0보다 클경우, 가져온 수량으로 저장하고, 차이만큼 불량으로 저장
						if(workOrderDto.getWorkOrder_PQty() - productQty >= 0) {
							workOrderDto.setWorkOrder_RQty(productQty);
							workOrderDto.setWorkOrder_DQty(workOrderDto.getWorkOrder_PQty() - productQty);
						}else
						//목표수량(설비1수량) - 가져온수량(설비2수량) 이 0보다 작을경우, 설비1수량으로 저장하고, 차이만큼 다음 작업지시에 반영
						if(workOrderDto.getWorkOrder_PQty() - productQty < 0) {
							workOrderDto.setWorkOrder_RQty(workOrderDto.getWorkOrder_PQty());
						}
						
						//설비2데이터로 업데이트해줌
						workOrderDto.setWorkOrder_EquipCode("M002");
						workOrderDao.workOrderUpdateDao(workOrderDto);
						
						//재고에 업데이트
						workOrderDao.workOrderStockUpdateDao(workOrderDto);
						
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int WorkOrderSeiyonNewUpdate(WorkOrderDto workOrderDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<ProductionMgmtDto> ProductionMgmtDtoList = workOrderSeiyonDao.workOrderSeiyonGapDao(workOrderDto);
					int M001_Qty = 0;
					int M002_Qty = 0;
					System.out.println(ProductionMgmtDtoList);

					for(int i=0;i<ProductionMgmtDtoList.size();i++) {
						if(ProductionMgmtDtoList.get(i).getPRODUCTION_Equipment_Code().equals("M001")) {

							M001_Qty = ProductionMgmtDtoList.get(i).getPRODUCTION_Volume();
						}else if(ProductionMgmtDtoList.get(i).getPRODUCTION_Equipment_Code().equals("M002")) {

							M002_Qty = ProductionMgmtDtoList.get(i).getPRODUCTION_Volume();
						}
					}
					workOrderDto.setWorkOrder_RQty(M002_Qty - M001_Qty);
					workOrderSeiyonDao.workOrderNewUpdateDao(workOrderDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
