 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotNoDao;
import com.busience.production.dao.WorkOrderDao;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dao.CrateProductionDao;
import com.busience.tablet.dao.RawMaterialDao;
import com.busience.tablet.dao.RawMaterialMasterDao;
import com.busience.tablet.dao.RawMaterialSubDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.CrateProductionDto;
import com.busience.tablet.dto.RawMaterialDto;
import com.busience.tablet.dto.RawMaterialMasterDto;
import com.busience.tablet.dto.RawMaterialSubDto;

@Service
public class MaskProductionService {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
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
	
	public List<RawMaterialDto> rawMaterialSelect(SearchDto searchDto) {
		return rawMaterialDao.rawMaterialSelectDao(searchDto);
	}
	
	//수량 업데이트
	public int rawMaterialQtyUpdate(RawMaterialMasterDto rawMaterialMasterDto) {
		return rawMaterialMasterDao.rawMaterialQtyUpdateDao(rawMaterialMasterDto);
	}
	
	// 설비명으로 조회
	public ItemDto workingSelectByMachine(SearchDto searchDto) {
		// 설비명으로 품목 조회
		String itemCode = workOrderDao.workingSelectByMachineDao(searchDto).get(0).getWorkOrder_ItemCode();

		return itemDao.selectItemCode(itemCode);		
	}
	
	
	//원자재 투입 저장
	public String rawMaterialSave(RawMaterialDto rawMaterialDto, List<RawMaterialSubDto> rawMaterialSubDtoList) {
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					for(int i=0;i<rawMaterialSubDtoList.size();i++) {
						rawMaterialDto.setMaterial_ItemCode(rawMaterialSubDtoList.get(i).getRMS_ItemCode());
						rawMaterialDto.setMaterial_LotNo(rawMaterialSubDtoList.get(i).getRMS_LotNo());
						rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
					}															
				}				
			});
			
			return null;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CrateDto crateSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateDao.crateSelectByMachineDao(searchDto);
	}
	
	public List<CrateLotDto> crateLotRecordSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateLotDao.crateLotRecordSelectDao(searchDto);
	}
	
	//상자 저장
	public CrateDto crateSave(CrateDto crateDto) {		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					//기존 값이 있으면 상태값 변경
					if(crateDto.getC_Before_CrateCode().length()>0) {
						CrateDto crateDtoTemp = new CrateDto();
						crateDtoTemp.setC_CrateCode(crateDto.getC_Before_CrateCode());
						crateDtoTemp.setC_Condition("2");
						crateDao.crateUpdateDao(crateDtoTemp);
					}

					//그 후 새로운 상자 등록
					String LotNo = lotNoDao.crateLotNoSelectDao(crateDto.getC_ItemCode());
					lotNoDao.lotNoMatUpdateDao();
					crateDto.setC_Condition("1");
					crateDto.setC_Production_LotNo(LotNo);
					crateDao.crateUpdateDao(crateDto);
					
					CrateLotDto crateLotDto = new CrateLotDto();
					crateLotDto.setCL_LotNo(LotNo);
					crateLotDto.setCL_ItemCode(crateDto.getC_ItemCode());
					crateLotDto.setCL_CrateCode(crateDto.getC_CrateCode());
					crateLotDto.setCL_MachineCode(crateDto.getC_MachineCode());
					crateLotDao.crateLotSaveDao(crateLotDto);
				}				
			});

			return crateDto;
			
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
					
					//crateLotNo 수정
					CrateLotDto crateLotDto = new CrateLotDto();
					crateLotDto.setCL_MachineCode(equip);
					crateLotDto.setCL_Qty(value);
					crateLotDao.crateLotQtyUpdateDao(crateLotDto);
					
				}				
			});			
			return 1;			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public CrateDto CrateStatusCheck(SearchDto searchDto) {
		return crateDao.crateStatusCheckDao(searchDto);
	}
	
	// 자재 투입 현황
	public List<CrateLotDto> crateLotSelectList(SearchDto searchDto) {
		return crateLotDao.crateLotSelectList(searchDto);
	}
}
