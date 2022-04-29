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
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.OutMatDao;
import com.busience.material.dao.RequestMasterDao;
import com.busience.material.dao.RequestSubDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.salesLX.dao.SalesInputLXDao;

@Service
public class MatOutputService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	StockDao stockDao;

	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	OutMatDao outMatDao;
	
	@Autowired
	RequestMasterDao requestMasterDao;
	
	@Autowired
	RequestSubDao requestSubDao;
	
	@Autowired
	SalesInputLXDao salesInputLXDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//LotMaster조회
	public List<LotMasterDto> LotMasterSelect(SearchDto searchDto){
		return lotMasterDao.lotMasterSelectDao(searchDto);
	}
	
	//등록
	public int outMatInsert(RequestSubDto requestSubDto, List<OutMatDto> outMatDtoList, String userCode){
		try {
			//판매구분
			//List<DtlDto> dtlDto = dtlDao.findByCode(18);
			List<DtlDto> wareHouseList = dtlDao.findByCode(10);
			//Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					/*
					for(int i=0;i<outMatDtoList.size();i++) {
						OutMatDto outMatDto = outMatDtoList.get(i);
						outMatDto.setOM_RequestNo(requestSubDto.getRS_RequestNo());
						outMatDto.setOM_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						outMatDto.setOM_Send_Clsfc(requestSubDto.getRS_Send_Clsfc());
						outMatDto.setOM_Modifier(userCode);
						
						//자재 창고 관련 업데이트 후에 생산 창고 관련 인서트 or 업데이트
						outMatDto.setOM_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						
						//랏마스터 업데이트
						lotMasterDao.lotMasterUpdateDao(outMatDtoList.get(i));
						//재고 업데이트
						stockDao.stockUpdateDao(outMatDtoList.get(i));
						
						//랏트랜스번호 가져오기
						int LotTransNo = lotTransDao.lotTransNoSelectDao2(outMatDtoList.get(i));
						outMatDto.setOM_No(LotTransNo);
						
						//이동 설정하기 자재창고 -> 생산창고
						outMatDto.setOM_Before(wareHouseList.get(0).getCHILD_TBL_NO());
						outMatDto.setOM_After(wareHouseList.get(1).getCHILD_TBL_NO());
						
						outMatDto.setOM_WareHouse(wareHouseList.get(1).getCHILD_TBL_NO());
						
						//랏트랜스
						lotTransDao.lotTransInsertDao2(outMatDtoList.get(i));
						
						//출고
						outMatDao.outMatInsertDao(outMatDtoList.get(i));
						
						//요청sub
						requestSubDao.RequestSubUpdateDao(outMatDtoList.get(i));
						
						//재고
						stockDao.stockInsertDao(outMatDtoList.get(i));

						//요청master
						requestMasterDao.requestMasterUpdateDao(outMatDtoList.get(i));
						
						//랏마스터 등록
						lotMasterDao.lotMasterInsertDao(outMatDtoList.get(i));
						
						if(dtlDto.get(3).getCHILD_TBL_NO().equals(outMatDtoList.get(i).getOM_Send_Clsfc())) {
							//품목코드
							sales_InMat_tbl.setSales_InMat_Code(outMatDtoList.get(i).getOM_ItemCode());
							//수량
							sales_InMat_tbl.setSales_InMat_Qty(outMatDtoList.get(i).getOM_Qty());
							//구분
							sales_InMat_tbl.setSales_InMat_Rcv_Clsfc(dtlDao.findByCode(19).get(0).getCHILD_TBL_NO());
							//사용자
							sales_InMat_tbl.setSales_InMat_Modifier(userCode);
							
							//판매출고일경우 영업창고에 입고처리해준다
							salesInputLXDao.salesInMatInsertDao(sales_InMat_tbl);
							
							salesInputLXDao.salesStockMatUpdateDao(sales_InMat_tbl);
						}
					}
					*/
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//출고조회
	public List<OutMatDto> matOutputList(SearchDto searchDto){
		return outMatDao.outMatListDao(searchDto);
	}
	
	//출고 조건별 조회
	public List<OutMatDto> matOutputOtherList(SearchDto searchDto){
		List<OutMatDto> outMatDtoList = outMatDao.outMatOtherListDao(searchDto);
		for(int i=0;i<outMatDtoList.size();i++) {
			String itemCode = outMatDtoList.get(i).getOM_ItemCode();
			String deptCode = outMatDtoList.get(i).getOM_DeptCode();
			
			if(itemCode == null || deptCode == null) {
				outMatDtoList.get(i).setOM_RequestNo("");
				outMatDtoList.get(i).setOM_LotNo("");
				outMatDtoList.get(i).setOM_OutDate("");
				outMatDtoList.get(i).setOM_Send_Clsfc_Name("Sub Total");
			}
			if(itemCode == null) {
				outMatDtoList.get(i).setOM_Item_Stnd_1("");
				outMatDtoList.get(i).setOM_Item_Stnd_2("");
				outMatDtoList.get(i).setOM_Item_CLSFC_1_Name("");
				outMatDtoList.get(i).setOM_Item_CLSFC_2_Name("");
				outMatDtoList.get(i).setOM_Unit("");
				outMatDtoList.get(i).setOM_ItemName("");
				
			}else if(deptCode == null) {
				outMatDtoList.get(i).setOM_DeptName("");
			}
			if(itemCode == null && deptCode == null) {
				outMatDtoList.get(i).setOM_Send_Clsfc_Name("Grand Total");
			}
		}
		return outMatDtoList;
	}
	
	//부서별 명세서 master
	public List<OutMatDto> matOutputDeliveryMaster(SearchDto searchDto){
		return outMatDao.outMatDeliveryMasterDao(searchDto);
	}
	
	//부서별 명세서 sub
	public List<OutMatDto> matOutputDeliverySub(SearchDto searchDto){
		return outMatDao.outMatDeliverySubDao(searchDto);
	}
}
