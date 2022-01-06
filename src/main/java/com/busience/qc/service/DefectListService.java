package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dao.DefectInsertDao;
import com.busience.qc.dao.DefectListDao;
import com.busience.qc.dto.DefectDto;

@Service
public class DefectListService {
	
	@Autowired
	DefectListDao defectListDao;
	
	@Autowired
	DefectInsertDao defectInsertDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//제품별 불량합계
	public List<WorkOrderDto> defectItemListSelect(SearchDto searchDto) {
        return defectListDao.defectItemListSelectDao(searchDto);
	}

	//설비별 불량합계
	public List<WorkOrderDto> defectMachineListSelect(SearchDto searchDto) {
        return defectListDao.defectMachineListSelectDao(searchDto);
	}
	
	//불량 서브데이터
	public List<DefectDto> defectListSub(SearchDto searchDto) {
		
		//defect_tbl의 리스트
		List<DefectDto> ONo_datas = defectListDao.defectListSubSelectDao(searchDto);
		//DEFECT_INFO_TBL의 리스트
		List<DefectDto> defectList = defectInsertDao.defectInfoDao();
		
		for(int i=0;i<defectList.size();i++) {			
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
}
