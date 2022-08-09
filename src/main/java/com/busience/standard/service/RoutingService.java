package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dao.RoutingDao;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.dto.RoutingDto;

@Service
public class RoutingService {
	
	@Autowired
	RoutingDao routingDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<ItemDto> routingItemSearch(SearchDto searchDto){
		return routingDao.routingItemSearchDao(searchDto);
	}
	
	//조회
	public List<RoutingDto> selectRoutingList() {
		return routingDao.selectRoutingListDao();
	}
	
	//특정 번호 조회
	public List<RoutingDto> selectRoutingDetail(RoutingDto routingDto) {
		return routingDao.selectRoutingDetailDao(routingDto);
	}
	
	//업데이트
	public int updateRouting(List<RoutingDto> routingDtoList) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<routingDtoList.size();i++) {
						
						RoutingDto routingDto = new RoutingDto();
						routingDto.setItem_Clsfc_1(routingDtoList.get(i).getItem_Clsfc_1());
						
						//1번
						routingDto.setRouting_No(1);
						routingDto.setRouting(routingDtoList.get(i).getRouting_1());
						routingDao.insertRoutingDao(routingDto);

						//2번
						routingDto.setRouting_No(2);
						routingDto.setRouting(routingDtoList.get(i).getRouting_2());
						routingDao.insertRoutingDao(routingDto);
						
						//3번
						routingDto.setRouting_No(3);
						routingDto.setRouting(routingDtoList.get(i).getRouting_3());
						routingDao.insertRoutingDao(routingDto);
						
						//4번
						routingDto.setRouting_No(4);
						routingDto.setRouting(routingDtoList.get(i).getRouting_4());
						routingDao.insertRoutingDao(routingDto);
						
						//5번
						routingDto.setRouting_No(5);
						routingDto.setRouting(routingDtoList.get(i).getRouting_5());
						routingDao.insertRoutingDao(routingDto);
						
						routingDao.updateRoutingDao(routingDtoList.get(i));
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
