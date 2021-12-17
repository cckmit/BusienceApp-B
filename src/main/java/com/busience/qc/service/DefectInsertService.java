package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dao.DefectInsertDao;
import com.busience.qc.dto.DefectDto;

@Service
public class DefectInsertService {
	
	@Autowired
	DefectInsertDao defectInsertDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//완료된 작업지시
	public List<WorkOrderDto> completeWorkOrder(SearchDto searchDto) {
        return defectInsertDao.completeWorkOrderDao(searchDto);
	}

	public List<DefectDto> defectList(String workOrder_ONo) {
		List<DefectDto> ONo_datas = defectInsertDao.defectListDao(workOrder_ONo);

		List<DefectDto> defectList = defectInsertDao.defectInfoDao();
		
		for(int i=0;i<defectList.size();i++) {
			defectList.get(i).setDefect_ONo(workOrder_ONo);
			
			if(ONo_datas.size()>0) {
				for(int j=0;j<ONo_datas.size();j++) {
					if(defectList.get(i).getDefect_Code().equals(ONo_datas.get(j).getDefect_Code())) {

						defectList.get(i).setDefect_Qty(ONo_datas.get(j).getDefect_Qty());
					}
				}
			}
		}
		
		return defectList;
	}
	
	public int defectSave(List<DefectDto> defectDtoList) {

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<defectDtoList.size();i++) {
						defectInsertDao.defectSaveDao(defectDtoList.get(i));
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
