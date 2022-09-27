package com.busience.material.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.OrderListDao;
import com.busience.material.dao.OrderMasterDao;
import com.busience.material.dao.StockDao;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockDto;

@Service
public class MatOrderService {

	@Autowired
	OrderMasterDao orderMasterDao;
	
	@Autowired
	OrderListDao orderListDao;
	
	@Autowired
	StockDao stockDao;
		
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//matOrderMaster조회	
	public List<OrderMasterDto> matOrderMasterSelect(SearchDto searchDto) {
		return orderMasterDao.orderMasterSelectDao(searchDto);
	}
	
	public OrderMasterDto orderMasterOneSelect(String orderNo) {
		return orderMasterDao.orderMasterOneSelectDao(orderNo);
	}
	
	//matOrderList조회	
	public List<OrderListDto> matOrderListSelect(SearchDto searchDto) {
		return orderListDao.orderListSelectDao(searchDto);
	}
		
	//stockMat조회	
	public List<StockDto> stockSelect(SearchDto searchDto) {
		return stockDao.stockSelectDao(searchDto);
	}
	
	//matOrderMasterInsertUpdate
	public int matOrderInsertUpdate(OrderMasterDto orderMasterDto, List<OrderListDto> orderListDtoList, String Modifier) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//발주번호가 없을경우 발주번호 생성
					if(orderMasterDto.getOrder_mCus_No() == null) {
						orderMasterDto.setOrder_mCus_No(orderMasterDao.orderNoCreateDao(orderMasterDto));
					}
					
					orderMasterDto.setOrder_mModifier(Modifier);
					orderMasterDao.orderMasterInsertUpdateDao(orderMasterDto);
					
					for(int i=0;i<orderListDtoList.size();i++) {
						OrderListDto orderListDto = orderListDtoList.get(i);
						orderListDto.setOrder_lCus_No(orderMasterDto.getOrder_mCus_No());
						
						orderListDao.orderListInsertUpdateDao(orderListDto);			
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
	public int matOrderListDelete(List<OrderListDto> orderListDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					String orderNo = orderListDtoList.get(0).getOrder_lCus_No();
					
					orderListDao.orderListDeleteDao(orderNo, orderListDtoList);
					orderMasterDao.orderMasterDeleteDao(orderNo);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
