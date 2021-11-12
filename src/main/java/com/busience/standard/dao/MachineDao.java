package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.MachineDto;

@Mapper
public interface MachineDao {

	//조회
	public List<MachineDto> selectMachineListDao();
	
	//등록
	public int insertMachineDao(MachineDto machineDto);
	
	//수정
	public int updateMachineDao(MachineDto machineDto);
	
	//삭제
	public int deleteMachineDao(String machineCode);
}
