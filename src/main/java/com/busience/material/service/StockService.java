package com.busience.material.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestMasterDto;
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
		return stockDao.stockSelectDao(searchDto);
	}

	// 재고 조정
	public List<StockDto> StockChanageSelect(StockDto stockDto) {
		return stockDao.stockChangeSelect(stockDto);
	}

	// 영업 재고테이블 조회
	public List<StockDto> salesStockSelectDao(SearchDto searchDto) {
		return stockDao.salesStockSelectDao(searchDto);
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
						RequestMasterDto requestMasterDto = new RequestMasterDto();

						outMatDto.setOM_Modifier(Modifier);
						outMatDto.setOM_LotNo(stockDtoList.get(i).getS_ItemCode());

						int no = i;
						String lotNo = stockDto.getS_LotNo();
						String itemCode = stockDto.getS_ItemCode();
						Double qty = (Double) stockDto.getS_ChangeQty();
						String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();

						outMatDto.setOM_Qty(qty);

						/*
						 * // 랏번호가 없을경우 랏번호 생성 if (lotNo == null || lotNo.isBlank()) { lotNo =
						 * lotNoDao.outMatlotNoSelectDao(outMatDto); outMatDto.setOM_LotNo(lotNo);
						 * 
						 * // 랏번호 업데이트 lotNoDao.lotNoMatUpdateDao(); }
						 */

						// 자재 요청에 저장하기 위해 부서 정보 불러옴
						UserDto userDto = userDao.selectUser(Modifier);

						// 작업자
						requestMasterDto.setRM_Modifier(Modifier);
						requestMasterDto.setRM_UserCode(Modifier);
						requestMasterDto.setRM_DeptCode(userDto.getDept_Code());
						
						// 자재 요청번호 생성
						String RequestNo = requestMasterDto.getRM_RequestNo();
						if (RequestNo.length() == 0) {
							RequestNo = requestMasterDto.getRM_DeptCode() + "-"
									+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
							RequestNo = RequestNo + "-" + requestMasterDao.requestNoSelectDao(RequestNo);
							requestMasterDto.setRM_RequestNo(RequestNo);
						}

						requestMasterDao.requestMasterInsertDao(requestMasterDto);
						
						// 변경 수량 만큼 RequestSub 저장
						for(int j=0; j<qty; j++) {
							requestSubDao.requestSubInsertDao(requestSubDtoList, RequestNo);
						}

						/*
						 * // 랏마스터 저장 lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty,
						 * Warehouse);
						 * 
						 * // 랏트랜스번호 가져오기 outMatDto.setOM_No(lotTransDao.lotTransNoSelectDao(lotNo)); //
						 * 재고 저장 stockDao.stockInsertUpdateDao(itemCode, qty, Warehouse);
						 * 
						 * outMatDto.setOM_Before(Warehouse);
						 * 
						 * // 자재출고 저장 outMatDto.outMatInsertDao(outMatDto);
						 * 
						 * // 랏트랜스 저장 lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before,
						 * after, classfy);
						 */

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
