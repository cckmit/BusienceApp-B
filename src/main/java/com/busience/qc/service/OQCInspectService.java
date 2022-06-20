package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dao.OQCInspectDao;
import com.busience.qc.dto.OQC_Inspect_Dto;
import com.busience.sales.dao.SalesOutputDao;
import com.busience.sales.dto.Sales_OutMat_tbl;

@Service
public class OQCInspectService {

	@Autowired
	SalesOutputDao salesOutputDao;
	
	@Autowired
	OQCInspectDao oqcInspectDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 영업 출고검사 할 리스트 조회
	public List<Sales_OutMat_tbl> salesOutMatInspectList(SearchDto searchDto) {
		return salesOutputDao.salesOutMatInspectList(searchDto);
	}
	
	// oqcInspectList
	public List<OQC_Inspect_Dto> oqcInspectList(SearchDto searchDto) {
		return oqcInspectDao.oqcInspectList(searchDto);
	}
	
	// oqcInspectOneSelect
	public List<OQC_Inspect_Dto> oqcInspectOneSelect(SearchDto searchDto) {
		return oqcInspectDao.oqcInspectOneSelect(searchDto);
	}
	
	// 저장
	public int oqcInspectInsertDao(List<OQC_Inspect_Dto> OQCInspectDtoList, List<OQC_Inspect_Dto> OQCPackUnitDtoList) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					int i = 0;
					
					for(int k = 0; k < 16; k++) {
						
						OQC_Inspect_Dto oqc_Inspect_Dto = OQCInspectDtoList.get(k);
						
						if(k > 8) {
							oqc_Inspect_Dto.setOQC_Inspect_Packing_Unit(OQCPackUnitDtoList.get(i).getOQC_Inspect_Packing_Unit());
							
							i++;
						}
						oqcInspectDao.oqcInspectInsertDao(oqc_Inspect_Dto);
					}
				}
			});
			
			return 1;
			
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
