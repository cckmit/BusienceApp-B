package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dao.BOMDao;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.dto.ItemDto;

@Service
public class BOMService {

	@Autowired
	BOMDao bomDao;
	
	//조회
	public List<ItemDto> BOMitemList(SearchDto searchDto) {
		return bomDao.BOMitemListDao(searchDto);
	}
	
	//조회
	public List<BOMDto> BOMBOMList(SearchDto searchDto) {
		return bomDao.BOMBOMListDao(searchDto);
	}
	
    //BOM - 원자재 검색
    public List<BOMDto> RawMaterialBOMList(SearchDto searchDto) {
        return bomDao.RawMaterialBOMListDao(searchDto);
    }
}
