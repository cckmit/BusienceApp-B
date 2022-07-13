package com.busience.production.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.production.dao.LabelPrintDao;
import com.busience.production.dto.LabelPrintDto;

@Service
public class LabelPrintService {

	@Autowired
	LabelPrintDao labelPrintDao;
	
	public List<LabelPrintDto> rawMaterialLabelSelect(List<SearchDto> searchDtoList) {
		List<LabelPrintDto> LabelPrintDtoList = new ArrayList<LabelPrintDto>();
		for(int i=0; i<searchDtoList.size(); i++) {
			LabelPrintDtoList.add(labelPrintDao.rawMaterialLabelSelectDao(searchDtoList.get(i).getLotNo()));
		}
		return LabelPrintDtoList;
	}
}
