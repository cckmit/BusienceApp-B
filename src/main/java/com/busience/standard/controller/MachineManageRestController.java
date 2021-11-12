package com.busience.standard.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.MachineDto;
import com.busience.standard.service.MachineService;

@RestController("machineManageRestController")
@RequestMapping("machineManageRest")
public class MachineManageRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MachineService machineService;

	@GetMapping("/machineManageSelect")
	public List<MachineDto> machineManageSelect() {
		return machineService.selectMachineList();
	}
	
	@PostMapping("/machineManageInsert")
	public int machineManageInsert(MachineDto machineDto){
		return machineService.insertMachine(machineDto);
	}
	
	@PutMapping("/machineManageUpdate")
	public int machineManageUpdate(MachineDto machineDto) {
		return machineService.updateMachine(machineDto);
	}

	@DeleteMapping("/machineManageDelete")
	public int machineManageDelete(MachineDto machineDto) {
		return machineService.deleteMachine(machineDto.getEQUIPMENT_INFO_CODE());

	}
}
