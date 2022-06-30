package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.qc.dto.DefectDto;
import com.busience.tablet.dto.CrateLotDto;

@Mapper
public interface CrateLotDao {

	//조회
	public CrateLotDto crateLotSelectDao(SearchDto searchDto);
	
	//이력조회
	public List<CrateLotDto> crateLotRecordSelectDao(SearchDto searchDto);
	
	//설비별목록
	public List<CrateLotDto> crateLotListSelectDao(SearchDto searchDto);
	
	//공정검사 상자 List 조회
	public List<CrateLotDto> crateInspectSelectDao(SearchDto searchDto);
	
	//마스크 투입 최신화 리스트
	public List<CrateLotDto> crateLotRefreshSelectDao(CrateLotDto crateLotDto);
	
	//lot 이력 조회
	public List<CrateLotDto> crateLotListMasterDao(SearchDto searchDto);
	
	//불량 list 조회
	public List<CrateLotDto> defectListDao(SearchDto searchDto);
	
	//자재 투입 현황 list
	public List<CrateLotDto> crateLotSelectList(SearchDto searchDto);
	
	//저장
	public int crateLotSaveDao(CrateLotDto crateLotDto);
	
	//수정
	public int crateLotUpdateDao(CrateLotDto crateLotDto);
	
	//수량수정
	public int crateLotQtyUpdateDao(CrateLotDto crateLotDto);
	
	//수정2
	public int crateInputQtyUpdateDao(CrateLotDto crateLotDto);
	
	//불량별 관리 -> 생산 수량 update
	public int crateQtyUpdateDao(CrateLotDto crateLotDto);
	
	//불량별 관리 - 수량 조회
	public int crateQtySelect(CrateLotDto crateLotDto);
}
