package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.productionLX.dao.ProCalendarDao;
import com.busience.productionLX.dto.ProComparedInput;

@Service
public class ProCalendarService {

	@Autowired
	
	ProCalendarDao proCalendarDao;
	
	// 총 생산량 조회
	public List<ProComparedInput> totalWorkOrderRQty(ProComparedInput proComparedInput) {
		return proCalendarDao.totalWorkOrderRQty(proComparedInput);
	}
	
	// 작업지시 총생산량
	public List<ProComparedInput> workOrderTotalQty(ProComparedInput proComparedInput) {
		return proCalendarDao.workOrderTotalQty(proComparedInput);
	}
	
	// 자재 출고 조회
	public List<ProComparedInput> outMatList(ProComparedInput proComparedInput) {
		return proCalendarDao.outMatList(proComparedInput);
	}
	
	// 자재 출고 수량
	public List<ProComparedInput> outMatTotalQty(ProComparedInput proComparedInput) {
		return proCalendarDao.outMatTotalQty(proComparedInput);
	}
}
