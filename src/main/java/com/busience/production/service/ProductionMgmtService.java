package com.busience.production.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.ProductionMgmtDao;
import com.busience.production.dto.ProductionMgmtDto;

@Service
public class ProductionMgmtService {
	
	@Autowired
	ProductionMgmtDao productionMgmtDao;
	
	// 생산 실적 관리(제품별)
	public List<ProductionMgmtDto> proItemSumList(SearchDto searchDto) {
		
		System.out.println(searchDto);
		List<ProductionMgmtDto> proItemSumList = productionMgmtDao.proItemSumDao(searchDto);
		
		for(ProductionMgmtDto dto : proItemSumList) {
			if(dto.getPRODUCTION_WorkOrder_ONo() == null || dto.getPRODUCTION_WorkOrder_ONo() == "") {
				dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
			    dto.setPRODUCTION_Product_Code("");
			    dto.setPRODUCTION_Product_Name("");
			    dto.setPRODUCTION_Equipment_Code("");
			    dto.setPRODUCTION_Equipment_Name("");
			    dto.setPRODUCTION_Info_STND_1("");
			    dto.setPRODUCTION_Item_CLSFC_NAME_1("");
			    dto.setPRODUCTION_Item_CLSFC_NAME_2("");
			    dto.setPRODUCTION_Material("");
			    dto.setPRODUCTION_Start_Date("");
				dto.setPRODUCTION_Date("");
				dto.setPRODUCTION_Total_Work_Time("");
			}
		} 
		
		return proItemSumList;
	}
	
	// 생산 실적 관리(설비별)
	public List<ProductionMgmtDto> proMachineSumList(SearchDto searchDto) {
		
		System.out.println(searchDto);
		List<ProductionMgmtDto> proMachineSumList = productionMgmtDao.proMachineSumDao(searchDto);
		
		for(ProductionMgmtDto dto : proMachineSumList) {
			if(dto.getPRODUCTION_WorkOrder_ONo() == null || dto.getPRODUCTION_WorkOrder_ONo() == "") {
				dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
			    dto.setPRODUCTION_Product_Code("");
			    dto.setPRODUCTION_Product_Name("");
			    dto.setPRODUCTION_Equipment_Code("");
			    dto.setPRODUCTION_Equipment_Name("");
			    dto.setPRODUCTION_Info_STND_1("");
			    dto.setPRODUCTION_Item_CLSFC_NAME_1("");
			    dto.setPRODUCTION_Item_CLSFC_NAME_2("");
			    dto.setPRODUCTION_Material("");
			    dto.setPRODUCTION_Start_Date("");
				dto.setPRODUCTION_Date("");
				dto.setPRODUCTION_Total_Work_Time("");
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
	public List<ProductionMgmtDto> proMachineList(SearchDto searchDto) {
		List<ProductionMgmtDto> resultList = productionMgmtDao.proMachineListDao(searchDto);

		for(ProductionMgmtDto dto : resultList) {
			if(dto.getPRODUCTION_WorkOrder_No() == 0) {
				if(dto.getPRODUCTION_Equipment_Code() == null) {
					dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				}else {
					dto.setPRODUCTION_WorkOrder_ONo("Sub Total");
				}
				dto.setPRODUCTION_Product_Code(null);
				dto.setPRODUCTION_Product_Name(null);
			}
		}
		return resultList;
	}
	
	//마스크 실적 현황
	public List<ProductionMgmtDto> proMaskSumDao(SearchDto searchDto) {
		
		List<ProductionMgmtDto> proMaskSumList = productionMgmtDao.proMaskSumDao(searchDto);
		
		for(ProductionMgmtDto dto : proMaskSumList) {
			if(dto.getPRODUCTION_WorkOrder_ONo() == null || dto.getPRODUCTION_WorkOrder_ONo() == "") {
				dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				dto.setPRODUCTION_Product_Code("");
				dto.setPRODUCTION_Product_Name("");
				dto.setPRODUCTION_Equipment_Code("");
				dto.setPRODUCTION_Equipment_Name("");
				dto.setPRODUCTION_Info_STND_1("");
				dto.setPRODUCTION_Item_CLSFC_NAME_1("");
				dto.setPRODUCTION_Item_CLSFC_NAME_2("");
				dto.setPRODUCTION_Material("");
				dto.setPRODUCTION_Start_Date("");
				dto.setPRODUCTION_Date("");
				dto.setPRODUCTION_Total_Work_Time("");
			}
		}
		return proMaskSumList;
	}
	
	//생산 포장 현황
	public List<ProductionMgmtDto> proPackingDao(SearchDto searchDto) {
		
		List<ProductionMgmtDto> proPacingSumList = productionMgmtDao.proPackingSumDao(searchDto);
		
		for(ProductionMgmtDto dto : proPacingSumList) {
			if(dto.getPRODUCTION_WorkOrder_ONo() == null || dto.getPRODUCTION_WorkOrder_ONo() == "") {
				dto.setPRODUCTION_WorkOrder_ONo("Grand Total");
				dto.setPRODUCTION_Product_Code("");
				dto.setPRODUCTION_Product_Name("");
				dto.setPRODUCTION_Equipment_Code("");
				dto.setPRODUCTION_Equipment_Name("");
				dto.setPRODUCTION_Info_STND_1("");
				dto.setPRODUCTION_Item_CLSFC_NAME_1("");
				dto.setPRODUCTION_Item_CLSFC_NAME_2("");
				dto.setPRODUCTION_Material("");
				dto.setPRODUCTION_Start_Date("");
				dto.setPRODUCTION_Date("");
				dto.setPRODUCTION_Total_Work_Time("");
			}
		}
		return proPacingSumList;
	}
	
	//Lot 발행 조회
	public List<ProductionMgmtDto> lotIssueListDao(SearchDto searchDto) {
		return productionMgmtDao.lotIssueListDao(searchDto);
	}
}
