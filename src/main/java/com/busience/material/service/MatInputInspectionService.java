package com.busience.material.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.InMatInspectDao;
import com.busience.material.dao.TemporaryStorageDao;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.InMatInspectDto;

@Service
public class MatInputInspectionService {

	@Autowired
	TemporaryStorageDao temporaryStorageDao;
	
	@Autowired
	InMatInspectDao inMatInspectDao;
	
	public List<InMatDto> temporaryStorageSelectDao(SearchDto searchDto) {
		System.out.println(searchDto);
		return temporaryStorageDao.temporaryStorageSelectDao(searchDto);
	}
	
	public int InMatInspectInsertDao(InMatInspectDto inMatInspectDto, String[] standard, String[] value1,
			String[] value2, String[] value3, String[] value4, String[] value5, String[] stnd1, String[] stnd2,
			String[] status) {
		// TODO Auto-generated method stub
		
		System.out.println("서비스단 : ");
		
		int inputData = 10;
		
		InMatInspectDto inMatInspectDtoData = new InMatInspectDto();
		
		for(int i=0; i < inputData; i++) {
			
			// standard controller에서 dto에 담아올것
			inMatInspectDtoData.setInMat_Inspect_Value_1(value1[i]);
			inMatInspectDtoData.setInMat_Inspect_Value_2(value1[i]);
			inMatInspectDtoData.setInMat_Inspect_Value_3(value3[i]);
			inMatInspectDtoData.setInMat_Inspect_Value_4(value4[i]);
			inMatInspectDtoData.setInMat_Inspect_Value_5(value4[i]);
			
			if(i >= 7) {
				inMatInspectDtoData.setInMat_Inspect_STND_1(stnd1[i]);
				inMatInspectDtoData.setInMat_Inspect_STND_2(stnd2[i]);
			} else {
				inMatInspectDtoData.setInMat_Inspect_STND_1(null);
				inMatInspectDtoData.setInMat_Inspect_STND_2(null);
			}
			
			inMatInspectDtoData.setInMat_Inspect_Status(status[i]);
			
			
		}
		
		System.out.println("inMatInspectDtoData = " + inMatInspectDtoData);
		
		
		return inMatInspectDao.InMatInspectInsertDao(inMatInspectDto);
		
	}
}
