package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dao.MachineDao;
import com.busience.standard.dto.MachineDto;

@Service
public class MachineService {

	@Autowired
	MachineDao machineDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
		
	//조회
	public List<MachineDto> selectMachineList() {
		return machineDao.selectMachineListDao();
	}
	
	//조회
	public MachineDto selectMachineInfo(SearchDto searchDto) {
		return machineDao.selectMachineInfoDao(searchDto);
	}
	
	//설비 종류에 따라 조회
	public List<MachineDto> dtlMachineList(MachineDto machineDto) {
		return machineDao.dtlMachineListDao(machineDto);
	}
	
	//등록
	public int insertMachine(MachineDto machineDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		machineDto.setEQUIPMENT_MODIFIER(authentication.getName());
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					machineDao.insertMachineDao(machineDto);
				}
				
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	};

	//수정
	public int updateMachine(MachineDto machineDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		machineDto.setEQUIPMENT_MODIFIER(authentication.getName());
		return machineDao.updateMachineDao(machineDto);
	};
	
	//삭제
	public int deleteMachine(String machineCode) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub
					machineDao.deleteMachineDao(machineCode);
				}
				
			});
			
			return 1;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	};
}
