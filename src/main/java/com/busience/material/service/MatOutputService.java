package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.material.dao.MatOutputDao;
import com.busience.material.dto.OutMat_tbl;
import com.busience.material.dto.StockMat_tbl;
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
	
	//조회	
	public List<StockMat_tbl> outMatList() {
		return matOutputDao.outMatListDao();
	}
	
	//등록
	public int matOutputRegister(List<OutMat_tbl> outMat_tbl_List, String userCode){
		try {
			//판매구분
			List<DtlDto> dtlDto = dtlDao.findByCode(18);
			Sales_InMat_tbl sales_InMat_tbl = new Sales_InMat_tbl();
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<outMat_tbl_List.size();i++) {
						outMat_tbl_List.get(i).setOutMat_Modifier(userCode);
						
						matOutputDao.outMatInsertDao(outMat_tbl_List.get(i));
						
						matOutputDao.stockMatUpdateDao(outMat_tbl_List.get(i));
						
						if(dtlDto.get(3).getCHILD_TBL_NO().equals(outMat_tbl_List.get(i).getOutMat_Send_Clsfc())) {
							//품목코드
							sales_InMat_tbl.setSales_InMat_Code(outMat_tbl_List.get(i).getOutMat_Code());
							//수량
							sales_InMat_tbl.setSales_InMat_Qty(outMat_tbl_List.get(i).getOutMat_Qty());
							//구분
							sales_InMat_tbl.setSales_InMat_Rcv_Clsfc(dtlDao.findByCode(19).get(0).getCHILD_TBL_NO());
							//사용자
							sales_InMat_tbl.setSales_InMat_Modifier(userCode);
							
							//판매출고일경우 영업창고에 입고처리해준다
							salesInputLXDao.salesInMatInsertDao(sales_InMat_tbl);
							
							salesInputLXDao.salesStockMatUpdateDao(sales_InMat_tbl);
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
