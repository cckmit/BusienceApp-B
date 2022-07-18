package com.busience.common.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dao.IotCheckDao;
import com.busience.common.dao.ProductionDao;
import com.busience.common.dao.TestCheckDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.IotCheckDto;
import com.busience.common.dto.ProductionDto;
import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dao.TemperatureMonitoringDao;
import com.busience.monitoring.dto.EquipMonitoringDto;
import com.busience.monitoring.dto.EquipTemperatureHistoryDto;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dto.EquipWorkOrderDto;
import com.busience.production.dto.WorkOrderDto;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.ItemDto;
import com.busience.tablet.service.MaskProductionService;

@Service
public class ProductionService {

	@Autowired
	IotCheckDao iotCheckDao;
	
	@Autowired
	ProductionDao productionDao;
	
	@Autowired
	TestCheckDao testCheckDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TemperatureMonitoringDao temperatureMonitoringDao;
	
	@Autowired
	MaskProductionService maskProductionService;
	
	@Autowired
	EquipWorkOrderDao equipWorkOrderDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	boolean pause = true;
	
	public boolean pauseChange(boolean TF) {
		if(TF) {
			pause = !pause;
		}
		return pause;
	}
	
	//생산량 저장
	public int insertProduction(String equip, int value) {
		
		//iot 접속 체크용
		IotCheckDto iotCheckDto = new IotCheckDto();
		iotCheckDto.setIot_EquipCode(equip);
		iotCheckDto.setIot_Value(value);
		iotCheckDao.IotInsertDao(iotCheckDto);
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//작업지시 옵션 검색
					List<DtlDto> dtlDtoList = dtlDao.findAllByCode(38);
					
					List<WorkOrderDto> workOrderDtoList = productionDao.selectWorkOrderDao(equip);
					
					//작업지시 있을때 맞춰서 인서트, 해당설비의 작업시작값이 꼭 1개
					if(workOrderDtoList.size()>0) {
						//해당 품목의 정보를 가져옴
						ItemDto itemDto = itemDao.selectItemCode(workOrderDtoList.get(0).getWorkOrder_ItemCode());	
						
						ProductionDto productionDto = new ProductionDto();
						productionDto.setPRODUCTION_WorkOrder_ONo(workOrderDtoList.get(0).getWorkOrder_ONo());
						productionDto.setPRODUCTION_Product_Code(workOrderDtoList.get(0).getWorkOrder_ItemCode());
						productionDto.setPRODUCTION_Equipment_Code(equip);
						
						//생산량 배수가 있으면 배수만큼 곱해줌 만약 0이거나 값이 없으면 들어온값 그대로
						if(itemDto.getPRODUCT_MULTIPLE()>0) {
							productionDto.setPRODUCTION_Volume(value*itemDto.getPRODUCT_MULTIPLE());
						}else {
							productionDto.setPRODUCTION_Volume(value);
						}
						
						//작업지시 옵션
						productionDto.setWorkOrder_Status(dtlDtoList.get(2).getCHILD_TBL_USE_STATUS());
						
						//작업지시테이블 업데이트
						productionDao.updateWorkOrderDao(productionDto);
					}
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//생산량 저장
	public int insertProductionPaldang(String equip, int value) {
		//iot 접속 체크용
		IotCheckDto iotCheckDto = new IotCheckDto();
		iotCheckDto.setIot_EquipCode(equip);
		iotCheckDto.setIot_Value(value);
		iotCheckDao.IotInsertDao(iotCheckDto);
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					SearchDto searchDto = new SearchDto();
					searchDto.setMachineCode(equip);
					
					List<EquipWorkOrderDto> EquipWorkOrderDtoList = equipWorkOrderDao.equipWorkOrderSelectDao(searchDto);
					
					if(EquipWorkOrderDtoList.size()>0) {
						String itemCode = EquipWorkOrderDtoList.get(0).getEquip_WorkOrder_ItemCode();
						//해당 품목의 정보를 가져
						ItemDto itemDto = itemDao.selectItemCode(itemCode);
						
						//자재식별코드, crate 수량 저장
						maskProductionService.wholeQtyUpdate(equip, value*itemDto.getPRODUCT_MULTIPLE());
					}
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	//work order 조회
	public List<WorkOrderDto> getWorkOrder(String code) {
    	return productionDao.selectWorkOrderDao(code);
	}
	
	//온도 저장
	public int insertTemperature(String equip, float value) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					SearchDto searchDto = new SearchDto();
					searchDto.setMachineCode(equip);
					
					//해당설비의 작업정보를 가져옴
					EquipMonitoringDto equipMonitoringDto = temperatureMonitoringDao.selectEquipMonitoringDao(searchDto).get(0);
					equipMonitoringDto.setTemp(value);
					
					EquipTemperatureHistoryDto equipTemperatureHistoryDto = temperatureMonitoringDao.selectEquipTemperatureHistoryDao(searchDto).get(0); 	
					
					//처음 입력하는 데이터 이거나
					//모니터링 테이블에 저장된 시간과 지금 저장하려는 값의 시간차이가 공통코드의 시간보다 클경우만 저장
					boolean testvalue = timeGap(equipTemperatureHistoryDto.getTemp_Time());
					
					if(testvalue) {
						temperatureMonitoringDao.insertTemperatureDao(equipMonitoringDto);
					}
					
					temperatureMonitoringDao.updateTemperatureDao(equipMonitoringDto);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//시간 차이
	private boolean timeGap(String saveTime) {
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime dateTime = LocalDateTime.parse(saveTime, DTF);

		//받아온 시간과 현재 시간의 차이
		Duration duration = Duration.between(dateTime, LocalDateTime.now());
		
		List<DtlDto> DtlDtoList = dtlDao.findByCode(31);
		System.out.println(DtlDtoList);
		//시간
		int timeCode = Integer.parseInt(DtlDtoList.get(2).getCHILD_TBL_RMARK()); 
		//시, 분
		String timeUnit = DtlDtoList.get(5).getCHILD_TBL_RMARK();

		
		if("분".equals(timeUnit)) {
			if(duration.toMinutesPart() >= timeCode) {
				return true;				
			}
		}else if("시".equals(timeUnit)){			
			if(duration.toHoursPart() >= timeCode) {
				return true;				
			}
		}
		
		return false;
	}
}
