package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.ProductionMgmtDao;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dao.RawMaterialDao;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.RawMaterialDto;

@Service
public class ProductionMgmtService {
	
	@Autowired
	ProductionMgmtDao productionMgmtDao;
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	RawMaterialDao rawMaterialDao;
	
	// 생산 실적 관리(제품별)
	public List<CrateLotDto> proItemSumList(SearchDto searchDto) {
		
		System.out.println(searchDto);
		List<CrateLotDto> proItemSumList = productionMgmtDao.proItemSumDao(searchDto);
		
		for(CrateLotDto dto : proItemSumList) {
			if(dto.getCL_LotNo() == null || dto.getCL_LotNo() == "") {
				dto.setCL_LotNo("Grand Total");
				dto.setCL_ItemCode("");
				dto.setCL_ItemName("");
				dto.setCL_MachineCode("");
				dto.setCL_EquipName("");
				dto.setCL_STND_1("");
				dto.setCL_Item_Clsfc_Name_1("");
				dto.setCL_Item_Clsfc_Name_2("");
				dto.setCL_Item_Material("");
				dto.setCL_Create_Date("");
			}
		} 
		
		return proItemSumList;
	}
	
	// 생산 실적 관리(설비별)
	public List<CrateLotDto> proMachineSumList(SearchDto searchDto) {
		
		System.out.println(searchDto);
		List<CrateLotDto> proMachineSumList = productionMgmtDao.proMachineSumDao(searchDto);
		
		for(CrateLotDto dto : proMachineSumList) {
			if(dto.getCL_LotNo() == null || dto.getCL_LotNo() == "") {
				dto.setCL_LotNo("Grand Total");
				dto.setCL_ItemCode("");
				dto.setCL_ItemName("");
				dto.setCL_MachineCode("");
				dto.setCL_EquipName("");
				dto.setCL_STND_1("");
				dto.setCL_Item_Clsfc_Name_1("");
				dto.setCL_Item_Clsfc_Name_2("");
				dto.setCL_Item_Material("");
				dto.setCL_Create_Date("");
			}
		} 
		
		return proMachineSumList;
	}
	
	//품목별
	public List<ProductionMgmtDto> proItemList(SearchDto searchDto) {
		List<ProductionMgmtDto> resultList = productionMgmtDao.proItemListDao(searchDto);
		
		for(ProductionMgmtDto dto : resultList) {
			if(dto.getPRODUCTION_WorkOrder_No() == 0) {
				if(dto.getPRODUCTION_Product_Code() == null) {
					dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				}else {
					dto.setPRODUCTION_WorkOrder_ONo("Sub Total");
				}
				dto.setPRODUCTION_Equipment_Code(null);
				dto.setPRODUCTION_Equipment_Name(null);
			}
		}
		return resultList;
	}

	//기계별
	public List<CrateLotDto> proMachineList(SearchDto searchDto) {
		List<CrateLotDto> resultList = productionMgmtDao.proMachineListDao(searchDto);

		for(CrateLotDto dto : resultList) {
			
			if(dto.getCL_LotNo() == null) {
				if(dto.getCL_MachineCode() == null) {
					dto.setCL_LotNo("Grand_Total");
				} else {
					dto.setCL_LotNo("Sub_Total");
				}
				dto.setCL_ItemCode("");
				dto.setCL_ItemName("");
			}
		}
		return resultList;
	}
	
	//마스크 실적 현황
	public List<CrateLotDto> proMaskSumDao(SearchDto searchDto) {
		
		List<CrateLotDto> proMaskSumList = productionMgmtDao.proMaskSumDao(searchDto);
		
		for(CrateLotDto dto : proMaskSumList) {
			if(dto.getCL_LotNo() == null || dto.getCL_LotNo() == "") {
				dto.setCL_LotNo("Grand Total");
				dto.setCL_ItemCode("");
				dto.setCL_ItemName("");
				dto.setCL_MachineCode("");
				dto.setCL_EquipName("");
				dto.setCL_STND_1("");
				dto.setCL_Item_Clsfc_Name_1("");
				dto.setCL_Item_Clsfc_Name_2("");
				dto.setCL_Item_Material("");
				dto.setCL_Create_Date("");
			}
		}
		return proMaskSumList;
	}
	
	//생산 포장 현황
	public List<CrateLotDto> proPackingDao(SearchDto searchDto) {
		
		List<CrateLotDto> proPacingSumList = productionMgmtDao.proPackingSumDao(searchDto);
		
		for(CrateLotDto dto : proPacingSumList) {
			if(dto.getCL_LotNo() == null || dto.getCL_LotNo() == "") {
				dto.setCL_LotNo("Grand Total");
				dto.setCL_ItemCode("");
				dto.setCL_ItemName("");
				dto.setCL_MachineCode("");
				dto.setCL_EquipName("");
				dto.setCL_STND_1("");
				dto.setCL_Item_Clsfc_Name_1("");
				dto.setCL_Item_Clsfc_Name_2("");
				dto.setCL_Item_Material("");
				dto.setCL_Create_Date("");
			}
		}
		return proPacingSumList;
	}
	
	//Lot 발행 조회
	public List<ProductionMgmtDto> lotIssueListDao(SearchDto searchDto) {
		return productionMgmtDao.lotIssueListDao(searchDto);
	}
	
	//Lot 이력 조회
	public List<CrateLotDto> crateLotListMasterDao(SearchDto searchDto) {
		return crateLotDao.crateLotListMasterDao(searchDto);
	}
	
	//Lot 이력 조회
	public List<RawMaterialDto> rawMaterialListMasterDao(SearchDto searchDto) {
		return rawMaterialDao.rawMaterialListMasterDao(searchDto);
	}
}
