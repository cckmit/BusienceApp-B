 package com.busience.tablet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.WorkOrderDao;
import com.busience.production.dto.WorkOrderDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class MaskInputService {
	
	@Autowired
	CrateDao crateDao;
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	WorkOrderDao workOrderDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<CrateLotDto> crateMachineSelect(SearchDto searchDto) {
		return crateLotDao.crateMachineSelectDao(searchDto);
	}
	
	public List<CrateLotDto> crateLotListSelect(SearchDto searchDto) {
		return crateLotDao.crateLotListSelectDao(searchDto);
	}
	
	public List<WorkOrderDto> maskInputSelect(){
		return workOrderDao.workOrderInputTabletDao();
	}
	
	public int crateLotUpdate2(CrateLotDto crateLotDto) {
		return crateLotDao.crateLotUpdateDao2(crateLotDto);
	}
	
	public int crateLotRefresh(CrateLotDto crateLotDto) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<CrateLotDto> refreshList = new ArrayList<CrateLotDto>();
					refreshList = crateLotDao.crateLotRefreshSelectDao(crateLotDto);
					
					for(int i=0;i<refreshList.size();i++) {
						crateLotDao.crateLotUpdateDao2(refreshList.get(i));
					}					
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int maskInputUpdate(CrateLotDto crateLotDto) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					CrateDto crateDto = new CrateDto();
					CrateLotDto crateLotDtoTemp = new CrateLotDto();
					SearchDto searchDto = new SearchDto();
					
					//Crate_tbl 업데이트
					crateDto.setC_CrateCode(crateLotDto.getCL_CrateCode());
					crateDto.setC_Condition("3");
					crateDao.crateUpdateDao(crateDto);
					
					//LotNo 탐색
					searchDto.setCrateCode(crateDto.getC_CrateCode());
					searchDto.setCondition(crateDto.getC_Condition());
					String LotNo = crateDao.crateStatusCheckDao(searchDto).getC_Production_LotNo();
					
					//Crate_Lot_tbl 업데이트
					crateLotDtoTemp.setCL_LotNo(LotNo);
					crateLotDtoTemp.setCL_MachineCode(crateLotDto.getCL_MachineCode());
					crateLotDao.crateLotUpdateDao(crateLotDtoTemp);					
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
