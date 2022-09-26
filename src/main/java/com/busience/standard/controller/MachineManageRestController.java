package com.busience.standard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dto.MachineDto;
import com.busience.standard.service.MachineService;

@RestController("machineManageRestController")
@RequestMapping("machineManageRest")
public class MachineManageRestController {

	@Autowired
	MachineService machineService;

	@GetMapping("/machineManageSelect")
	public List<MachineDto> machineManageSelect() {
		return machineService.selectMachineList();
	}
	
	@GetMapping("/selectMachineInfo")
	public MachineDto selectMachineInfo(SearchDto searchDto) {
		return machineService.selectMachineInfo(searchDto);
	}
	
	@GetMapping("/dtlMachineList")
	public List<MachineDto> dtlMachineList(MachineDto machineDto) {
		return machineService.dtlMachineList(machineDto);
	}
	
	@GetMapping("/labelMachineList")
	public List<MachineDto> labelMachineListDao() {
		return machineService.labelMachineListDao();
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
