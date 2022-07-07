 package com.busience.tablet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dao.SmallPackagingDao;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.dto.LabelPrintDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.sales.dao.SalesInputDao;
import com.busience.sales.dto.Sales_InMat_tbl;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dao.paldangPackagingStandardDao;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.dto.PaldangPackagingStandardDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class MaskPackagingService {
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	ItemDao itemDao;
	
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
	LotMasterDao lotMasterDao;

	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	SalesInputDao salesInputDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	paldangPackagingStandardDao paldangPackagingDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	
	@Transactional
	public LabelPrintDto smallPackagingSave(SearchDto searchDto) {
		List<DtlDto> wareHouseList = dtlDao.findByCode(10);
		
		String itemCode = searchDto.getItemCode();
		
		//품목정보에서 규격2를 가져옴
		ItemDto itemDto = itemDao.selectItemCode(itemCode);
		//규격2로 포장규격을 가져옴
		String STND2 = itemDto.getPRODUCT_INFO_STND_2();
		
		PaldangPackagingStandardDto paldangPackagingStandardDto = new PaldangPackagingStandardDto();
		paldangPackagingStandardDto.setPackaging_No(STND2);
		paldangPackagingStandardDto = paldangPackagingDao.paldangPackagingCheckNo(paldangPackagingStandardDto).get(0);
		
		//소포장 LotNo
		String small_Packaging_LotNo = lotNoDao.smallLotNoSelectDao(itemCode);
		lotNoDao.lotNoMatUpdateDao();
		
		String production_LotNo;
		String machineCode = searchDto.getMachineCode();
		double totalQty = paldangPackagingStandardDto.getPackaging_Small();
		double Qty = 0;

		//포장설비 리스트를 가져옴 수량이 적은 순서대로
		List<EquipWorkOrderDto> packagingLine = equipWorkOrderDao.packagingLineListSelect2Dao(searchDto);
		
		//포장수량/설비갯수
		double divideQty = totalQty/packagingLine.size();
		double restQty = totalQty%packagingLine.size();
		double packagingQty = 0;
		
		//분배된 포장수량을 설비별로 랏저장
		for(int i=0;i<packagingLine.size();i++) {
			SearchDto machineList = new SearchDto();
			machineList.setMachineCode(packagingLine.get(i).getEquip_WorkOrder_Code());
			
			List<CrateLotDto> CrateLotDtoList = crateLotDao.crateLotListSelectDao(machineList);
			
			System.out.println("시작1 "+Qty);
			
			packagingQty = divideQty + Qty;
			System.out.println("시작2 "+packagingQty);
			
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
				
				CrateLotDtoList.get(j).setCL_ProductionQty(-1 * crateQty);
				packagingQty = packagingQty - crateQty;		

				crateLotDao.crateInputQtyUpdateDao(CrateLotDtoList.get(j));
				
				Small_Packaging_tbl small_Packaging_tbl = new Small_Packaging_tbl();
				small_Packaging_tbl.setSmall_Packaging_LotNo(small_Packaging_LotNo);
				small_Packaging_tbl.setProduction_LotNo(production_LotNo);
				small_Packaging_tbl.setMachineCode(machineCode);
				small_Packaging_tbl.setItemCode(itemCode);
				small_Packaging_tbl.setQty(crateQty);
				smallPackagingDao.smallPackagingInsertDao(small_Packaging_tbl);

				System.out.println("끝 "+packagingQty);
				Qty = packagingQty;
				if(packagingQty == 0) {								
					break;
				}
			}
			
			Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
			
			sales_InMat_tbl.setSales_InMat_No(lotTransDao.lotTransNoSelectDao2(small_Packaging_LotNo));
			sales_InMat_tbl.setSales_InMat_Lot_No(small_Packaging_LotNo);
			sales_InMat_tbl.setSales_InMat_Code(itemCode);
			sales_InMat_tbl.setSales_InMat_Qty((int) totalQty);
			sales_InMat_tbl.setSales_InMat_Before(wareHouseList.get(1).getCHILD_TBL_NO());
			sales_InMat_tbl.setSales_InMat_After(wareHouseList.get(2).getCHILD_TBL_NO());
			sales_InMat_tbl.setSales_InMat_WareHouse(wareHouseList.get(2).getCHILD_TBL_NO());
			sales_InMat_tbl.setSales_InMat_Rcv_Clsfc("203");
			
			String Warehouse = sales_InMat_tbl.getSales_InMat_WareHouse();
			
			// lotMaster tbl insert
			lotMasterDao.salesLotMasterInsertUpdateDao(small_Packaging_LotNo, itemCode, totalQty, Warehouse);

			int LotTranseNo = sales_InMat_tbl.getSales_InMat_No();
			String Before = "";
			String After = sales_InMat_tbl.getSales_InMat_After();
			String Send_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
			
			lotTransDao.lotTransInsertDao2(LotTranseNo, small_Packaging_LotNo, itemCode, totalQty, Before, After, Send_Clsfc);
			
			sales_InMat_tbl.setSales_InMat_Modifier("admin");
			
			// sales inMat tbl insert
			salesInputDao.salesInMatInsertDao(sales_InMat_tbl);
			
			// stock tbl insert
			stockDao.stockInsertDao(itemCode, Qty, Warehouse);
		}
		return labelPrintDao.smallPackagingLabelSelectDao(small_Packaging_LotNo);
	}
}
