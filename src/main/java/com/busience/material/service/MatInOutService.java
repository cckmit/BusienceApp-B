package com.busience.material.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dto.LotTransDto;

@Service
public class MatInOutService {
	
	@Autowired
	LotTransDao lotTransDao;

	//입출고 현황
	public List<LotTransDto> MatInOutSelect(SearchDto searchDto){
		List<LotTransDto> LotTransDtoListStandard = lotTransDao.inOutMatSelectDao(searchDto);
		List<LotTransDto> LotTransDtoListMaster = new ArrayList<LotTransDto>();
		
		String itemCode = null;		
		int inQtySum = 0;
		int inReturnSum = 0;
		int inOtherSum = 0;
		int OutQtySum = 0;
		int OutReturnQtySum = 0;
		int OutOtherQtySum = 0;
		
		for(int i=0;i<LotTransDtoListStandard.size();i++) {
			LotTransDto LotTransDto = new LotTransDto();
			
			if(LotTransDtoListStandard.get(i).getLT_ItemCode().equals(itemCode)) {
				//품목 두번째 데이터부터 나올 데이터
				LotTransDto.setLT_InQty(LotTransDtoListStandard.get(i).getLT_InQty());
				LotTransDto.setLT_InReturn_Qty(LotTransDtoListStandard.get(i).getLT_InReturn_Qty());
				LotTransDto.setLT_InOther_Qty(LotTransDtoListStandard.get(i).getLT_InOther_Qty());
				LotTransDto.setLT_OutQty(LotTransDtoListStandard.get(i).getLT_OutQty());
				LotTransDto.setLT_OutReturn_Qty(LotTransDtoListStandard.get(i).getLT_OutReturn_Qty());
				LotTransDto.setLT_OutOther_Qty(LotTransDtoListStandard.get(i).getLT_OutOther_Qty());
				LotTransDto.setLT_Classify_Name(LotTransDtoListStandard.get(i).getLT_Classify_Name());
				LotTransDtoListMaster.add(LotTransDto);
			}else {
				if(itemCode != null) {
					//합계를 나타낼 데이터
					LotTransDto.setLT_ItemName("Total");
					LotTransDto.setLT_InQty(inQtySum);
					LotTransDto.setLT_InReturn_Qty(inReturnSum);
					LotTransDto.setLT_InOther_Qty(inOtherSum);
					LotTransDto.setLT_OutQty(OutQtySum);
					LotTransDto.setLT_OutReturn_Qty(OutReturnQtySum);
					LotTransDto.setLT_OutOther_Qty(OutOtherQtySum);
					LotTransDtoListMaster.add(LotTransDto);
					
					//숫자 초기화
					inQtySum = 0;
					inReturnSum = 0;
					inOtherSum = 0;
					OutQtySum = 0;
					OutReturnQtySum = 0;
					OutOtherQtySum = 0;
				}

				//품목당 첫째줄을 나타낼 데이터
				LotTransDtoListMaster.add(LotTransDtoListStandard.get(i));				
			}
			//합계를 나타낼 데이터 저장
			itemCode = LotTransDtoListStandard.get(i).getLT_ItemCode();

			inQtySum += LotTransDtoListStandard.get(i).getLT_InQty();
			inReturnSum += LotTransDtoListStandard.get(i).getLT_InReturn_Qty();
			inOtherSum += LotTransDtoListStandard.get(i).getLT_InOther_Qty();
			OutQtySum += LotTransDtoListStandard.get(i).getLT_OutQty();
			OutReturnQtySum += LotTransDtoListStandard.get(i).getLT_OutOther_Qty();
			OutOtherQtySum += LotTransDtoListStandard.get(i).getLT_OutOther_Qty();
			
		}
		
		LotTransDto LotTransDto = new LotTransDto();
		//마지막합계를 나타낼 데이터
		LotTransDto.setLT_ItemName("Total");
		LotTransDto.setLT_InQty(inQtySum);
		LotTransDto.setLT_InReturn_Qty(inReturnSum);
		LotTransDto.setLT_InOther_Qty(inOtherSum);
		LotTransDto.setLT_OutQty(OutQtySum);
		LotTransDto.setLT_OutReturn_Qty(OutReturnQtySum);
		LotTransDto.setLT_OutOther_Qty(OutOtherQtySum);
		LotTransDtoListMaster.add(LotTransDto);
				
		return LotTransDtoListMaster;
	}
}
