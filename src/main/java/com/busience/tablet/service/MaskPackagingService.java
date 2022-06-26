 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotNoDao;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dao.SmallPackagingDao;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.dto.LabelPrintDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class MaskPackagingService {
	
	@Autowired
	EquipWorkOrderDao equipWorkOrderDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
	@Autowired
	CrateDao crateDao;
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	SmallPackagingDao smallPackagingDao;
	
	@Autowired
	LabelPrintDao labelPrintDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Transactional
	public LabelPrintDto smallPackagingSave(SearchDto searchDto) {
		String itemCode = searchDto.getItemCode();
		
		//소포장 LotNo
		String small_Packaging_LotNo = lotNoDao.smallLotNoSelectDao(itemCode);
		lotNoDao.lotNoMatUpdateDao();
		
		String production_LotNo;
		String machineCode = searchDto.getMachineCode();
		double qty;
		
		//포장설비 리스트를 가져옴
		List<EquipWorkOrderDto> packagingLine = equipWorkOrderDao.packagingLineListSelectDao(searchDto);
		
		//포장수량/설비갯수
		double divideQty = 1000/packagingLine.size();
		double restQty = 1000%packagingLine.size();
		
		//분배된 포장수량을 설비별로 랏저장
		for(int i=0;i<packagingLine.size();i++) {
			SearchDto machineList = new SearchDto();
			machineList.setMachineCode(packagingLine.get(i).getEquip_WorkOrder_Code());
			
			List<CrateLotDto> CrateLotDtoList = crateLotDao.crateLotListSelectDao(machineList);
			
			double packagingQty = divideQty;
			if(restQty > 0) {
				packagingQty++;
				restQty--;
			}
			
			for(int j=0;j<CrateLotDtoList.size();j++) {
				double crateQty = CrateLotDtoList.get(j).getCL_ProductionQty();
				production_LotNo = CrateLotDtoList.get(j).getCL_LotNo();
				searchDto.setLotNo(production_LotNo);
				
				if(crateQty > packagingQty) {
					crateQty = packagingQty;
				}else {
					CrateDto crateDto = new CrateDto();
					crateDto.setC_CrateCode(CrateLotDtoList.get(j).getCL_CrateCode());							
					crateDto.setC_Production_LotNo("");
					crateDto.setC_Condition("0");
					crateDao.crateUpdateDao(crateDto);
				}
				qty = crateQty;
				CrateLotDtoList.get(j).setCL_ProductionQty(-1 * qty);
				packagingQty = packagingQty - qty;		

				crateLotDao.crateInputQtyUpdateDao(CrateLotDtoList.get(j));
				
				Small_Packaging_tbl small_Packaging_tbl = new Small_Packaging_tbl();
				small_Packaging_tbl.setSmall_Packaging_LotNo(small_Packaging_LotNo);
				small_Packaging_tbl.setProduction_LotNo(production_LotNo);
				small_Packaging_tbl.setMachineCode(machineCode);
				small_Packaging_tbl.setItemCode(itemCode);
				small_Packaging_tbl.setQty(qty);
				smallPackagingDao.smallPackagingInsertDao(small_Packaging_tbl);

				if(packagingQty == 0) {								
					break;
				}
			}
		}
		return labelPrintDao.smallPackagingLabelSelectDao(small_Packaging_LotNo);
	}
}
