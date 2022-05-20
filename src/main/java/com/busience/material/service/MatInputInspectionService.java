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

	// 선택 조회
	public List<InMatInspectDto> matInspectOneSelectDao(SearchDto searchDto) {
		return inMatInspectDao.matInspectOneSelectDao(searchDto);
	}

	public int InMatInspectInsertDao(InMatInspectDto inMatInspectDto, InMatInspectDto standardData,
			List<InMatInspectDto> value1List, List<InMatInspectDto> value2List, List<InMatInspectDto> value3List,
			List<InMatInspectDto> value4List, List<InMatInspectDto> value5List, List<InMatInspectDto> stnd1List,
			List<InMatInspectDto> stnd2List, List<InMatInspectDto> statusList, InMatDto inMatData, String userCode) {
		// TODO Auto-generated method stub

		try {

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					
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
					
					// 자재입고 테이블 update
					inMatDao.inMatCheckUpdateDao(inMatData);

				}

			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
