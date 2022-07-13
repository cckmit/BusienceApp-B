package com.busience.monitoring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.SearchDto;
import com.busience.monitoring.dao.ProductionStatusMonitoringDao;
import com.busience.monitoring.dto.ChangedProductionStatusMonitoringDto;
import com.busience.monitoring.dto.ChangedWrappingStatusMonitoringDto;
import com.busience.monitoring.dto.ProductionStatusMonitoringDto;
import com.busience.monitoring.dto.WrappingStatusMonitoringDto;

@Service
public class ProductionMonitoringService {

	@Autowired
	DtlDao dtlDao;

	@Autowired
	ProductionStatusMonitoringDao productionStatusMonitoringDao;

	@Autowired
	TransactionTemplate transactionTemplate;

	// 생산 모니터링 조회
	public List<ProductionStatusMonitoringDto> selectProductionMonitoring(SearchDto searchDto) {
		return productionStatusMonitoringDao.getProductionMonitoringDao(searchDto);
	}

	// 포장 모니터링 조회
	public List<WrappingStatusMonitoringDto> selectWrappingMonitoring(SearchDto searchDto) {
		return productionStatusMonitoringDao.getWrappingMonitoringDao(searchDto);
	}
		
	// 바뀐 생산 모니터링 조회
	public List<ChangedProductionStatusMonitoringDto> selectChangedProductionMonitoring(SearchDto searchDto) {
		return productionStatusMonitoringDao.getChangedProductionMonitoringDao(searchDto);
	}

	// 바뀐 포장 모니터링 조회
	public List<ChangedWrappingStatusMonitoringDto> selectChangedWrappingMonitoring(SearchDto searchDto) {
		return productionStatusMonitoringDao.getChangedWrappingMonitoringDao(searchDto);
	}
}
