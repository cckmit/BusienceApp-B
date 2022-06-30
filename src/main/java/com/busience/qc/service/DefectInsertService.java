package com.busience.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.qc.dao.DefectInsertDao;
import com.busience.qc.dto.DefectDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;

@Service
public class DefectInsertService {

	@Autowired
	DefectInsertDao defectInsertDao;

	@Autowired
	CrateLotDao crateLotDao;

	@Autowired
	CrateDao crateDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	// 완료된 작업지시
	public List<WorkOrderDto> completeWorkOrder(SearchDto searchDto) {
		return defectInsertDao.completeWorkOrderDao(searchDto);
	}

	// 불량 list 조회
	public List<CrateLotDto> defectListDao(SearchDto searchDto) {
		return crateLotDao.defectListDao(searchDto);
	}

	public List<DefectDto> defectList(String workOrder_ONo) {
		// defect_tbl의 리스트
		List<DefectDto> ONo_datas = defectInsertDao.defectListDao(workOrder_ONo);
		// DEFECT_INFO_TBL의 리스트
		List<DefectDto> defectList = defectInsertDao.defectInfoDao();

		for (int i = 0; i < defectList.size(); i++) {
			defectList.get(i).setDefect_ONo(workOrder_ONo);

			if (ONo_datas.size() > 0) {
				for (int j = 0; j < ONo_datas.size(); j++) {
					if (defectList.get(i).getDefect_Code().equals(ONo_datas.get(j).getDefect_Code())) {

						defectList.get(i).setDefect_Qty(ONo_datas.get(j).getDefect_Qty());
					}
				}
			}
		}

		return defectList;
	}

	public int defectSave(CrateLotDto crateLotDto, List<DefectDto> defectList) {

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for (int i = 0; i < defectList.size(); i++) {
						defectInsertDao.defectSaveDao(defectList.get(i));
						// crateLot 수량 반영
						if (defectList.get(i).getDefect_Qty() != 0) {
							// crate lot 수량 update
							crateLotDto.setCL_Defect_Qty(defectList.get(i).getDefect_Qty());
							crateLotDao.crateQtyUpdateDao(crateLotDto);
							// crate lot 수량 0인 것 조회
							int code = crateLotDao.crateQtySelect(crateLotDto);

							if (code > 0) {
								CrateDto crateDto = new CrateDto();
								crateDto.setC_CrateCode(crateLotDto.getCL_CrateCode());
								crateDto.setC_Condition("0");
								crateDto.setC_Production_LotNo("");
								crateDao.crateUpdateDao(crateDto);
							}
						} 
					}
				}
			});

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
