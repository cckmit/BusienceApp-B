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
import com.busience.material.dao.MatOutputDao;
import com.busience.material.dto.LotMasterDto;
import com.busience.material.dto.OutMatDto;
import com.busience.material.dto.RequestSubDto;
import com.busience.salesLX.dao.SalesInputLXDao;
import com.busience.salesLX.dto.Sales_InMat_tbl;

@Service
public class MatOutputService {

	@Autowired
	MatOutputDao matOutputDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	SalesInputLXDao salesInputLXDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//LotMaster조회
	public List<LotMasterDto> LotMasterSelect(SearchDto searchDto){
		return matOutputDao.LotMasterSelectDao(searchDto);
	}
	
	//등록
	public int outMatInsert(RequestSubDto requestSubDto,List<OutMatDto> outMatDtoList, String userCode){
		try {
			System.out.println(requestSubDto);
			System.out.println(outMatDtoList);
			//판매구분
			List<DtlDto> dtlDto = dtlDao.findByCode(18);
			List<DtlDto> wareHouseList = dtlDao.findByCode(10);
			Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<outMatDtoList.size();i++) {
						outMatDtoList.get(i).setOM_RequestNo(requestSubDto.getRS_RequestNo());
						outMatDtoList.get(i).setOM_WareHouse(wareHouseList.get(0).getCHILD_TBL_NO());
						outMatDtoList.get(i).setOM_Send_Clsfc(requestSubDto.getRS_Send_Clsfc());
						outMatDtoList.get(i).setOM_Modifier(requestSubDto.getRS_RequestNo());
						
						matOutputDao.outMatInsertDao(outMatDtoList.get(i));
						
						matOutputDao.stockUpdateDao(outMatDtoList.get(i));
						/*
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
						}*/
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
