package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.productionLX.dto.ProComparedInput;

@Mapper
public interface ProCalendarDao {

	// 총 생산량 조회
	public List<ProComparedInput> totalWorkOrderRQty(ProComparedInput proComparedInput);
	
	// 작업지시 총생산량
	public List<ProComparedInput> workOrderTotalQty(ProComparedInput proComparedInput);
	
	// 자재 출고 조회
	public List<ProComparedInput> outMatList(ProComparedInput proComparedInput);
	
	// 자재 출고 수량
	public List<ProComparedInput> outMatTotalQty(ProComparedInput proComparedInput);
}
