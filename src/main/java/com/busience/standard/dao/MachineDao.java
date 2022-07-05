package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dto.MachineDto;

@Mapper
public interface MachineDao {

	//조회
	public List<MachineDto> selectMachineListDao();

	//조회
	public MachineDto selectMachineInfoDao(SearchDto searchDto);
	
	//설비 종류에 따라 조회
	public List<MachineDto> dtlMachineListDao(MachineDto machineDto);
	
	//라벨 프린터 리스트 조회
	public List<MachineDto> labelMachineListDao();
	
	//등록
	public int insertMachineDao(MachineDto machineDto);
	
	//수정
	public int updateMachineDao(MachineDto machineDto);
	
	//삭제
	public int deleteMachineDao(String machineCode);
}
