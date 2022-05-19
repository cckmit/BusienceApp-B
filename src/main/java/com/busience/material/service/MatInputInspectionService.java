package com.busience.material.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.InMatDao;
import com.busience.material.dao.InMatInspectDao;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dao.TemporaryStorageDao;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.InMatInspectDto;

@Service
public class MatInputInspectionService {

	@Autowired
	DtlDao dtlDao;

	@Autowired
	LotNoDao lotNoDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	InMatDao inMatDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TemporaryStorageDao temporaryStorageDao;

	@Autowired
	InMatInspectDao inMatInspectDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	public List<InMatDto> temporaryStorageSelectDao(SearchDto searchDto) {
		System.out.println(searchDto);
		return temporaryStorageDao.temporaryStorageSelectDao(searchDto);
	}
	
	// 선택 조회
	public List<InMatInspectDto> matInspectOneSelectDao(SearchDto searchDto) {
		return inMatInspectDao.matInspectOneSelectDao(searchDto);
	}

	public int InMatInspectInsertDao(InMatInspectDto inMatInspectDto, InMatInspectDto standardData,
			List<InMatInspectDto> value1List, List<InMatInspectDto> value2List, List<InMatInspectDto> value3List,
			List<InMatInspectDto> value4List, List<InMatInspectDto> value5List, List<InMatInspectDto> stnd1List,
			List<InMatInspectDto> stnd2List, List<InMatInspectDto> statusList, String userCode) {
		// TODO Auto-generated method stub

		try {

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);
					
					InMatDto inMatDto = new InMatDto();
					
					for (int i = 0; i < 10; i++) {

						standardData.setInMat_Inspect_Number(i + 1);
						standardData.setInMat_Inspect_Value_1(value1List.get(i).getInMat_Inspect_Value_1());
						standardData.setInMat_Inspect_Value_2(value2List.get(i).getInMat_Inspect_Value_2());
						standardData.setInMat_Inspect_Value_3(value3List.get(i).getInMat_Inspect_Value_3());
						standardData.setInMat_Inspect_Value_4(value4List.get(i).getInMat_Inspect_Value_4());
						standardData.setInMat_Inspect_Value_5(value5List.get(i).getInMat_Inspect_Value_5());

						if (i == 7) {
							standardData.setInMat_Inspect_STND_1(stnd1List.get(0).getInMat_Inspect_STND_1());
							standardData.setInMat_Inspect_STND_2(stnd2List.get(0).getInMat_Inspect_STND_2());
						}

						if (i == 8) {
							standardData.setInMat_Inspect_STND_1(stnd1List.get(1).getInMat_Inspect_STND_1());
							standardData.setInMat_Inspect_STND_2(stnd2List.get(1).getInMat_Inspect_STND_2());
						}

						if (i == 9) {
							standardData.setInMat_Inspect_STND_1(stnd1List.get(2).getInMat_Inspect_STND_1());
							standardData.setInMat_Inspect_STND_2(stnd2List.get(2).getInMat_Inspect_STND_2());
						}

						standardData.setInMat_Inspect_Status(statusList.get(i).getInMat_Inspect_Status());

						System.out.println("standardData = " + standardData);

						// 입고 검사 테이블 저장
						inMatInspectDao.InMatInspectInsertDao(standardData);

					}

					// 가입고 테이블 update
					String TS_OrderNo = standardData.getInMat_Inspect_Order_No();
					String TS_ItemCode = standardData.getInMat_Inspect_ItemCode();
					temporaryStorageDao.temporaryStorageUpdateDao(TS_OrderNo, TS_ItemCode);
					
					inMatDto.setInMat_Order_No(standardData.getInMat_Inspect_Order_No());
					inMatDto.setInMat_Code(standardData.getInMat_Inspect_ItemCode());
					inMatDto.setInMat_Qty(standardData.getInMat_Inspect_Qty());
					inMatDto.setInMat_Rcv_Clsfc(standardData.getInMat_Inspect_Classfy());

					String lotNo = inMatDto.getInMat_Lot_No();
					String itemCode = inMatDto.getInMat_Code();
					double qty = (double) inMatDto.getInMat_Qty();
					String Warehouse = WarehouseList.get(0).getCHILD_TBL_NO();
					String before = "";
					String after = WarehouseList.get(0).getCHILD_TBL_NO();
					String classfy = inMatDto.getInMat_Rcv_Clsfc();

					// 랏번호가 없을경우 랏번호 생성
					if (lotNo == null || lotNo.isBlank()) {
						lotNo = lotNoDao.lotNoSelectDao(inMatDto);
						inMatDto.setInMat_Lot_No(lotNo);

						// 랏번호 업데이트
						lotNoDao.lotNoMatUpdateDao();
					}

					// 자재창고에 저장
					inMatDto.setInMat_Warehouse(Warehouse);
					// 랏트랜스번호 가져오기
					inMatDto.setInMat_No(lotTransDao.lotTransNoSelectDao(lotNo));
					int no = inMatDto.getInMat_No();
					// 고객 정보
					inMatDto.setInMat_Client_Code(standardData.getInMat_Inspect_Customer());
					
					// 입고일
					Date date = new Date();
				    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String now = format.format(date);
					inMatDto.setInMat_Date(now);
					// 단가
					inMatDto.setInMat_Unit_Price(standardData.getInMat_Inspect_UnitPrice());
					// 이동 설정하기 외부 -> 자재창고
					inMatDto.setInMat_Before(before);
					inMatDto.setInMat_After(after);
					// 작업자
					inMatDto.setInMat_Modifier(userCode);

					// 랏마스터 저장
					lotMasterDao.lotMasterInsertUpdateDao(lotNo, itemCode, qty, Warehouse);

					// 재고 저장
					stockDao.stockInsertUpdateDao(itemCode, qty, Warehouse);

					// 자재입고 저장
					inMatDao.inMatInsertDao(inMatDto);

					// 랏트랜스 저장
					lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);
				}

			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
}
