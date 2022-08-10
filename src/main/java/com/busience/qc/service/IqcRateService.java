package com.busience.qc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dao.IqcRateDao;
import com.busience.qc.dto.IqcDto;

@Service
public class IqcRateService {
	
	@Autowired
	IqcRateDao iqcRateDao;

	// 완제품 데이터 조회
	public List<IqcDto> iqcRateSelectDao(SearchDto searchDto) {
		List<IqcDto> InMatInspectDtoList = iqcRateDao.iqcRateSelectDao(searchDto);
		List<IqcDto> tempList = new ArrayList<IqcDto>();
		
		for(int i=0;i<InMatInspectDtoList.size();i++) {
			double pass_Qty = InMatInspectDtoList.get(i).getPass_Qty();
			double fail_Qty = InMatInspectDtoList.get(i).getFail_Qty();

			if(pass_Qty + fail_Qty > 0) {
				double total_Qty = pass_Qty + fail_Qty;
				double pass_Rate = Math.round(pass_Qty/total_Qty*10000)/100;
				InMatInspectDtoList.get(i).setTotal_Qty(total_Qty);
				InMatInspectDtoList.get(i).setPass_Rate(pass_Rate);
				tempList.add(InMatInspectDtoList.get(i));
			}
		}
				
		return tempList;
	}
}
