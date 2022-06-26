package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.RequestMasterDao;
import com.busience.material.dao.RequestSubDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.material.dto.StockDto;
import com.busience.standard.dao.UserDao;
import com.busience.standard.dto.UserDto;

@Service
public class StockService {

	@Autowired
	DtlDao dtlDao;

	@Autowired
	StockDao stockDao;

	@Autowired
	LotMasterDao lotMasterDao;

	@Autowired
	LotTransDao lotTransDao;

	@Autowired
	LotNoDao lotNoDao;

	@Autowired
	OutMatDao outMatDao;

	@Autowired
	UserDao userDao;

	@Autowired
	RequestMasterDao requestMasterDao;

	@Autowired
	RequestSubDao requestSubDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	// 재고테이블 조회
	public List<StockDto> StockSelect(SearchDto searchDto) {

		List<StockDto> stockList = stockDao.stockSelectDao(searchDto);

		for (StockDto dto : stockList) {
			if (dto.getS_ItemCode() == null || dto.getS_ItemCode() == "") {
				dto.setS_ItemCode("Grand Total");
				dto.setS_ItemName("");
				dto.setS_Item_Standard_1("");
				dto.setS_Item_Classfy_1_Name("");
				dto.setS_Item_Classfy_2_Name("");
				dto.setS_Item_Unit("");
			}
		}
		return stockList;
	}

	// 재고 Lot-품목 조회
	public List<StockDto> stockLotSelectDao(SearchDto searchDto) {
		return stockDao.stockLotSelectDao(searchDto);
	}

	// 재고 조정
	public List<StockDto> StockChanageSelect(StockDto stockDto) {
		return stockDao.stockChangeSelect(stockDto);
	}

	// 영업 재고테이블 조회
	public List<StockDto> salesStockSelectDao(SearchDto searchDto) {
		return stockDao.salesStockSelectDao(searchDto);
	}

	// 영업 재고 Lot-품목 조회
	public List<StockDto> salesStockLotSelectDao(SearchDto searchDto) {
		return stockDao.salesStockLotSelectDao(searchDto);
	}

	// 출고지시수량 재고 확인
	public List<StockDto> salesOutputStockDao(SearchDto searchDto) {
		return stockDao.salesOutputStockDao(searchDto);
	}

	// 출고지시조회 재고 확인
	public List<StockDto> salesOutputOrderStockDao(SearchDto searchDto) {
		return stockDao.salesOutputOrderStockDao(searchDto);
	}

	// 재고 조정 저장
	public int StockChangeSave(List<StockDto> stockDtoList, List<RequestSubDto> requestSubDtoList, String Modifier) {

		System.out.println(stockDtoList);
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);

					System.out.println(stockDtoList);

					for (int i = 0; i < stockDtoList.size(); i++) {
						StockDto stockDto = stockDtoList.get(i);
						System.out.println(stockDtoList.get(i));
						OutMatDto outMatDto = new OutMatDto();
						UserDto userDto = userDao.selectUser(Modifier);

						outMatDto.setOM_Modifier(Modifier);
						outMatDto.setOM_ItemCode(stockDtoList.get(i).getS_ItemCode());

						int no = i;
						String lotNo = stockDto.getS_LotNo();
						String itemCode = stockDto.getS_ItemCode();
						Double changeqty;
						Double qty;
						Double outMatQty;

						String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
						String before = Warehouse;
						String after = Warehouse;

						String classfy = "345";

						changeqty = (Double) stockDto.getS_ChangeQty();
						outMatQty = (Double) stockDto.getS_Qty() - (Double) stockDto.getS_ChangeQty();

						outMatDto.setOM_RequestNo("99-221231-01");

						outMatDto.setOM_DeptCode(userDto.getDept_Code());

						outMatDto.setOM_Before(Warehouse);

						outMatDto.setOM_After(Warehouse);

						outMatDto.setOM_Send_Clsfc("344");

						no = lotTransDao.lotTransNoSelectDao(lotNo);

						// 랏번호가 없을경우 랏번호 생성
						if (lotNo == null || lotNo.isBlank()) {
							lotNo = lotNoDao.outMatlotNoSelectDao(outMatDto);
							outMatDto.setOM_LotNo(lotNo);

							// 랏번호 업데이트
							lotNoDao.lotNoMatUpdateDao();

						}

						System.out.println(lotNo);

						qty = changeqty;

						// 랏마스터 저장
						lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

						// 랏트랜스번호 가져오기
						outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo));

						outMatDto.setOM_LotNo(lotNo);

						qty = changeqty;

						// 랏트랜스 저장
						lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);

						qty = outMatQty;

						outMatDto.setOM_Qty(qty);

						// 자재출고 저장
						outMatDao.outMatInsertDao(outMatDto);

						Double stockQty = (Double) stockDto.getS_ChangeQty() - (Double) stockDto.getS_Qty();

						outMatQty = (double) stockQty;

						stockDao.stockInsertUpdateDao(itemCode, outMatQty, Warehouse);

					}
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		// return stockDao.stockChangeSave(stockDto);
	}

}
