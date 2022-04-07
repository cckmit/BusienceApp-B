package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.MatOrderDao;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockDto;

@Service
public class MatOrderService {

	@Autowired
	MatOrderDao matOrderDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//matOrderMaster조회	
	public List<OrderMasterDto> matOrderMasterSelect(SearchDto searchDto) {
		return matOrderDao.matOrderMasterSelectDao(searchDto);
	}
	
	//matOrderList조회	
	public List<OrderListDto> matOrderListSelect(SearchDto searchDto) {
		return matOrderDao.matOrderListSelectDao(searchDto);
	}
	
	//stockMat조회	
	public List<StockDto> stockSelect(SearchDto searchDto) {
		return matOrderDao.stockSelectDao(searchDto);
	}
	
	//matOrderMasterInsertUpdate
	public int matOrderInsertUpdate(OrderMasterDto orderMasterDto, List<OrderListDto> orderListDtoList, String Modifier) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//발주번호가 없을경우 발주번호 생성
					if(orderMasterDto.getOrder_mCus_No().length() == 0) {
						orderMasterDto.setOrder_mCus_No(matOrderDao.matOrderNoCreateDao(orderMasterDto));
					}
					
					orderMasterDto.setOrder_mModifier(Modifier);
					matOrderDao.matOrderMasterInsertUpdateDao(orderMasterDto);
					
					for(int i=0;i<orderListDtoList.size();i++) {
						OrderListDto orderListDto = orderListDtoList.get(i);
						orderListDto.setOrder_lCus_No(orderMasterDto.getOrder_mCus_No());
						
						matOrderDao.matOrderListInsertUpdateDao(orderListDto);			
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//OrderMaster 삭제
	public int matOrderMasterDelete(OrderMasterDto orderMasterDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					SearchDto searchDto = new SearchDto();
					searchDto.setOrderNo(orderMasterDto.getOrder_mCus_No());
					List<OrderListDto> orderListDtoList = matOrderListSelect(searchDto);
					
					matOrderListDelete(orderListDtoList);
					
					matOrderDao.matOrderMasterDeleteDao(orderMasterDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//OrderMaster 삭제
	public int matOrderListDelete(List<OrderListDto> orderListDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<orderListDtoList.size();i++) {
						OrderListDto orderListDto = orderListDtoList.get(i);						
						matOrderDao.matOrderListDeleteDao(orderListDto);			
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
