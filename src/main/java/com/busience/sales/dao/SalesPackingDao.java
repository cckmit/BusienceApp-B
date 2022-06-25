package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.sales.dto.SalesPackingDto;

@Mapper
public interface SalesPackingDao {
	
	// sales_packingNo create
	public SalesPackingDto salesPackingNoCreateDao(SalesPackingDto salesPackingDto);
	
	// sales_packing_select
	public List<SalesPackingDto> salesPackingListSelectDao(SearchDto searchDto);
	
	// 입고 반품 조회
	public List<SalesPackingDto> salesInMatReturnSelectDao(SearchDto searchDto);
	
	// 입고 반품 리스트
	public List<SalesPackingDto> salesInMatReturnListDao(SearchDto searchDto);
	
	// 포장 관리 조회
	public List<SalesPackingDto> salesPackingSelectDao(SearchDto searchDto);
	
	// 대포장 Lot 조회
	public List<SalesPackingDto> salesLargePackingLotNoDao(SearchDto searchDto);
	
	// 소포장 Lot 조회
	public List<SalesPackingDto> salesSmallPackingLotNoDao(SearchDto searchDto);
	
	// sales_packing_tbl insert
	public int salesPackingInsertDao(SalesPackingDto salesPackingDto);
	
	// sales_packing_tbl update
	public int salesPackingUpdateDao(SalesPackingDto salesPackingDto);

	//대포장 lot발행 저장
	public int largePackagingInsertDao(String LotNo);
}
