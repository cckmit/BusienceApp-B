package com.busience.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dto.LabelPrintDto;
import com.busience.sales.dao.SalesInputDao;
import com.busience.sales.dao.SalesPackingDao;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.dto.Sales_InMat_tbl;

@Service
public class SalesPackingService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	SalesPackingDao salesPackingDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
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
	
	
	// sales_packing_select
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesPackingListSelectDao(searchDto);
	}
	
	// 입고 반품 조회
	public List<SalesPackingDto> salesInMatReturnSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesInMatReturnSelectDao(searchDto);
	}
	
	// 입고 반품 리스트
	public List<SalesPackingDto> salesInMatReturnListDao(SearchDto searchDto) {
		return salesPackingDao.salesInMatReturnListDao(searchDto);
	}
	
	// 포장 관리 조회
	public List<SalesPackingDto> salesPackingSelectDao(SearchDto searchDto) {
		return salesPackingDao.salesPackingSelectDao(searchDto);
	}
	
	// 대포장 Lot 조회
	public List<SalesPackingDto> salesLargePackingLotNoDao(SearchDto searchDto) {
		return salesPackingDao.salesLargePackingLotNoDao(searchDto);
	}
	
	// 소포장 Lot 조회
	public List<SalesPackingDto> salesSmallPackingLotNoDao(SearchDto searchDto) {
		return salesPackingDao.salesSmallPackingLotNoDao(searchDto);
	}
	
	// 대포장 Lot 발행 저장
	@Transactional
	public LabelPrintDto largePackagingInsert(SearchDto searchDto) {
		List<DtlDto> wareHouseList = dtlDao.findByCode(10);
		
		String LotNo = lotNoDao.largeLotNoSelectDao(searchDto.getItemCode());
		searchDto.setLotNo(LotNo);
		lotNoDao.lotNoMatUpdateDao();

		salesPackingDao.largePackagingInsertDao(searchDto);
		LabelPrintDto LabelPrintDto = labelPrintDao.largePackagingLabelSelectDao(LotNo);
		
		Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
		
		sales_InMat_tbl.setSales_InMat_No(lotTransDao.lotTransNoSelectDao2(LotNo));
		sales_InMat_tbl.setSales_InMat_Lot_No(LotNo);
		sales_InMat_tbl.setSales_InMat_Code(LabelPrintDto.getItemCode());
		sales_InMat_tbl.setSales_InMat_Qty((int) LabelPrintDto.getQty());
		sales_InMat_tbl.setSales_InMat_Before("");
		sales_InMat_tbl.setSales_InMat_After(wareHouseList.get(2).getCHILD_TBL_NO());
		sales_InMat_tbl.setSales_InMat_WareHouse(wareHouseList.get(2).getCHILD_TBL_NO());
		sales_InMat_tbl.setSales_InMat_Rcv_Clsfc("203");
		
		String ItemCode = LabelPrintDto.getItemCode();
		double Qty = LabelPrintDto.getQty();
		String Warehouse = sales_InMat_tbl.getSales_InMat_WareHouse();
		
		// lotMaster tbl insert
		lotMasterDao.salesLotMasterInsertUpdateDao(LotNo, ItemCode, Qty, Warehouse);

		int LotTranseNo = sales_InMat_tbl.getSales_InMat_No();
		String Before = "";
		String After = sales_InMat_tbl.getSales_InMat_After();
		String Send_Clsfc = sales_InMat_tbl.getSales_InMat_Rcv_Clsfc();
		
		lotTransDao.lotTransInsertDao2(LotTranseNo, LotNo, ItemCode, Qty, Before, After, Send_Clsfc);
		
		sales_InMat_tbl.setSales_InMat_Modifier("admin");
		
		// sales inMat tbl insert
		salesInputDao.salesInMatInsertDao(sales_InMat_tbl);
		
		// stock tbl insert
		stockDao.stockInsertDao(ItemCode, Qty, Warehouse);
		
		return LabelPrintDto;
	}
	
	public int largePackagingQtySelect(SearchDto searchDto) {
		return salesPackingDao.largePackagingQtySelectDao(searchDto);
	}
}
