package com.busience.salesLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.salesLX.dao.SalesLabelPrintDao;
import com.busience.standard.dto.ItemDto;

@Service
public class SalesLabelPrintService {

	@Autowired
	SalesLabelPrintDao salesLabelPrintDao;
	
	//조회
	public List<ItemDto> salesInputList() {
		return salesLabelPrintDao.salesLabelPrintListDao();
	}
}
