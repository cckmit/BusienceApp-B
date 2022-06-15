 package com.busience.tablet.service;

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
import com.busience.tablet.dao.CrateProductionDao;
import com.busience.tablet.dao.RawMaterialDao;
import com.busience.tablet.dao.RawMaterialMasterDao;
import com.busience.tablet.dao.RawMaterialSubDao;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.CrateProductionDto;
import com.busience.tablet.dto.RawMaterialDto;
import com.busience.tablet.dto.RawMaterialMasterDto;
import com.busience.tablet.dto.RawMaterialSubDto;

@Service
public class MaskProductionService {

	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	RawMaterialMasterDao rawMaterialMasterDao;
	
	@Autowired
	RawMaterialSubDao rawMaterialSubDao;
	
	@Autowired
	RawMaterialDao rawMaterialDao;
	
	@Autowired
	CrateDao crateDao;
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	CrateProductionDao crateProductionDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<RawMaterialMasterDto> rawMaterialMasterSelect(SearchDto searchDto) {
		return rawMaterialMasterDao.rawMaterialMasterSelectDao(searchDto);
	}
	
	public List<RawMaterialMasterDto> rawMaterialRecordSelect(SearchDto searchDto){
		return rawMaterialMasterDao.rawMaterialRecordSelectDao(searchDto);
	}
	
	public List<RawMaterialSubDto> rawMaterialSubSelect(SearchDto searchDto) {
		return rawMaterialSubDao.rawMaterialSubSelectDao(searchDto);
	}
	
	//수량 업데이트
	public int rawMaterialQtyUpdate(RawMaterialMasterDto rawMaterialMasterDto) {
		return rawMaterialMasterDao.rawMaterialQtyUpdateDao(rawMaterialMasterDto);
	}
	
	// 설비명으로 조회
	public List<WorkOrderDto> workingSelectByMachine(SearchDto searchDto) {
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 설비명으로 작업지시 조회 후
					workOrderDao.workingSelectByMachineDao(searchDto);
					//작업지시번호로 자재식별코드 검색
					
					//작업지시번호로 상자lot 검색
				}				
			});
			
			return workOrderDao.workingSelectByMachineDao(searchDto);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String rawMaterialSave(RawMaterialDto rawMaterialDto) {
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
				}				
			});
			
			return null;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	//원자재 투입 저장
	public String rawMaterialSave1(RawMaterialMasterDto rawMaterialMasterDto, List<RawMaterialSubDto> rawMaterialSubDtoList) {
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					if(rawMaterialMasterDto.getRMM_Before_Production_ID().length() > 0) {
						//전 id값이 있다면 업데이트 후 저장
						rawMaterialMasterDao.rawMaterialMasterUpdateDao(rawMaterialMasterDto);
					}
					//새로운 식별코드 생성
					String Production_ID = rawMaterialMasterDao.rawMaterialLotNoSelectDao(rawMaterialMasterDto);
					
					rawMaterialMasterDto.setRMM_Production_ID(Production_ID);
					rawMaterialMasterDao.rawMaterialMasterSaveDao(rawMaterialMasterDto);
					
					for(int i=0;i<rawMaterialSubDtoList.size();i++) {
						rawMaterialSubDtoList.get(i).setRMS_Production_ID(Production_ID);
						rawMaterialSubDao.rawMaterialSubSaveDao(rawMaterialSubDtoList.get(i));
					}															
				}				
			});
			
			return rawMaterialMasterDto.getRMM_Production_ID();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	public List<CrateLotDto> crateSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateLotDao.crateLotSelectDao(searchDto);
	}
	
	public List<CrateLotDto> crateLotRecordSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateLotDao.crateLotRecordSelectDao(searchDto);
	}
	
	// 코드 조건으로 조회
	public List<CrateLotDto> crateSave(CrateLotDto crateLotDto) {
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//기존 값이 있으면 상태값 변경
					//그 후 새로운 상자 등록
					if(crateLotDto.getCL_Before_LotNo().length()>0) {
						crateLotDao.crateLotUpdateDao(crateLotDto);
					}
					String LotNo = crateLotDao.crateLotNoCreateDao(crateLotDto);
					
					crateLotDto.setCL_LotNo(LotNo);
					crateLotDao.crateLotSaveDao(crateLotDto);					
				}				
			});

			SearchDto searchDto = new SearchDto();
			searchDto.setOrderNo(crateLotDto.getCL_OrderNo());
			return crateLotDao.crateLotSelectDao(searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 코드 조건으로 조회
	public int crateProductionSave(CrateProductionDto crateProductionDto) {
		return crateProductionDao.crateProductionSaveDao(crateProductionDto);
	}
	
	public int wholeQtyUpdate(String equip, double value) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					SearchDto searchDto = new SearchDto();
										
					searchDto.setMachineCode(equip);
					 
					//작업지시 가져오기
					List<WorkOrderDto> workOrderDtoList = workOrderDao.workingSelectByMachineDao(searchDto);
					searchDto.setOrderNo(workOrderDtoList.get(0).getWorkOrder_ONo());
					
					//crateLotNo 가져오기
					CrateLotDto crateLotDto = crateLotDao.crateLotSelectDao(searchDto).get(0);
					crateLotDto.setCL_Qty(value);
					crateLotDao.crateLotQtyUpdateDao(crateLotDto);
					
					//자재식별코드 가져오기
					RawMaterialMasterDto rawMaterialMasterDto = rawMaterialMasterDao.rawMaterialMasterSelectDao(searchDto).get(0);
					rawMaterialMasterDto.setRMM_Qty(value);
					rawMaterialMasterDao.rawMaterialQtyUpdateDao(rawMaterialMasterDto);
				}				
			});			
			return 1;			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
