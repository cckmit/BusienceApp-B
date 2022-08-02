package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dto.CrateDto;

@Service
public class CrateService {

	@Autowired
	CrateDao crateDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<CrateDto> crateSelectDao(CrateDto crateDto) {
		
		List<CrateDto> CrateDtoList = crateDao.crateSelectDao(crateDto);
		
        for(CrateDto dto : CrateDtoList) {
        	if(dto.getC_MachineName2() == null) {
        		dto.setCL_Update_Date(null);
        	}
        }
		return CrateDtoList;
	}
	
	public int crateSave(List<CrateDto> crateDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<crateDtoList.size();i++) {
						crateDao.crateSaveDao(crateDtoList.get(i));
					}
				}
			});

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int crateUpdateDao(CrateDto crateDto) {
		return crateDao.crateUpdateDao(crateDto);
	}
	
	public CrateDto crateSelectByMachine(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateDao.crateSelectByMachineDao(searchDto.getMachineCode(), searchDto.getCondition());
	}
}
