package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busience.standard.dao.MachineDao;
import com.busience.standard.dto.MachineDto;

@Service
public class MachineService {

	@Autowired
	MachineDao machineDao;
	
	//조회
	public List<MachineDto> selectMachineList() {
		return machineDao.selectMachineListDao();
	}
	
	//등록
	public int insertMachine(MachineDto machineDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		machineDto.setEQUIPMENT_MODIFIER(authentication.getName());
		
		int result = 0;
		
		try {
			result = machineDao.insertMachineDao(machineDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	};
	
	//수정
	public int updateMachine(MachineDto machineDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		machineDto.setEQUIPMENT_MODIFIER(authentication.getName());
		return machineDao.updateMachineDao(machineDto);
	};
	
	//삭제
	public int deleteMachine(String machineCode) {
		return machineDao.deleteMachineDao(machineCode);
	};
}
