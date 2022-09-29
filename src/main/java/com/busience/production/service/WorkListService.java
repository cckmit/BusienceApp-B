package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.WorkOrderDao;
import com.busience.production.dto.WorkOrderDto;

@Service
public class WorkListService {

	@Autowired
	WorkOrderDao workOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	public List<WorkOrderDto> workListSelect(SearchDto searchDto) {
		return workOrderDao.workListSelectDao(searchDto);
	}
	
	public int workListSave(WorkOrderDto workOrderDto) {
		try {
			int result = transactionTemplate.execute(new TransactionCallback<Integer>() {  
				
				@Override                                                                      
				public Integer doInTransaction(TransactionStatus status) {
					int save = 0;
					WorkOrderDto orginData = workOrderDao.workOrderOneSelectDao(workOrderDto.getWorkOrder_ONo());
					
					int beforeNo = Integer.parseInt(orginData.getWorkOrder_WorkStatus());
					int afterNo = Integer.parseInt(workOrderDto.getWorkOrder_WorkStatus());
					save = afterNo - beforeNo;
					
					if(save == 1) {
						//한단계씩 상승
						//스타트타임이 없는경우 = 작업시작으로변경
						//앤드타임이 없는경우 = 완료로 변경
						if(workOrderDto.getWorkOrder_StartTime() == "") {
							if(workOrderDao.workOrderStartCheckDao(workOrderDto.getWorkOrder_EquipCode()) == 0) {
								workOrderDao.workListSaveDao(workOrderDto);
							}else {
								save = 0;
							}
						}else {
							if(workOrderDto.getWorkOrder_ProductionQty() == 0) {
								workOrderDao.workOrderDeleteDao(workOrderDto);
								save = 3;
							}else {
								workOrderDao.workListSaveDao(workOrderDto);
							}							
						}
					}
					return save;
				}                                                                              
			});
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
}
