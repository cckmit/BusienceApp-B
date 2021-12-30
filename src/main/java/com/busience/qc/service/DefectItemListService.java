package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.qc.dao.DefectInsertDao;
import com.busience.qc.dao.DefectItemListDao;
import com.busience.qc.dto.DefectDto;

@Service
public class DefectItemListService {
	
	@Autowired
	DefectItemListDao defectItemListDao;
	
	@Autowired
	DefectInsertDao defectInsertDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<WorkOrderDto> defectItemListSelect(SearchDto searchDto) {
        return defectItemListDao.defectItemListSelectDao(searchDto);
	}

	public List<DefectDto> defectList(SearchDto searchDto) {
		
		//defect_tbl의 리스트
		List<DefectDto> ONo_datas = defectItemListDao.defectItemListSubSelectDao(searchDto);
		//DEFECT_INFO_TBL의 리스트
		List<DefectDto> defectList = defectInsertDao.defectInfoDao();
		
		for(int i=0;i<defectList.size();i++) {
			defectList.get(i).setDefect_ItemCode(searchDto.getItemCode());
			
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
