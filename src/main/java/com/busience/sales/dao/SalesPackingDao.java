package com.busience.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.sales.dto.SalesPackingDto;
import com.busience.sales.dto.Sales_InMat_tbl;

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
	
	// 대포장 Lot 조회
	public List<SalesPackingDto> salesLargePackingLotNo2Dao(SearchDto searchDto);
	
	// 소포장 Lot 조회
	public List<Small_Packaging_tbl> salesSmallPackingLotNoDao(SearchDto searchDto);
	
	public List<Small_Packaging_tbl> salesSmallPackingLotNo2Dao(SearchDto searchDto);
	
	//대포장 라벨 수량
	public int largePackagingQtySelectDao(SearchDto searchDto);
	
	// sales_packing_tbl insert
	public int salesPackingInsertDao(SalesPackingDto salesPackingDto);
	
	// sales_packing_tbl update
	public int salesPackingUpdateDao(Sales_InMat_tbl sales_InMat_tbl);

	//대포장 lot발행 저장
	public int largePackagingInsertDao(SearchDto searchDto);
}
