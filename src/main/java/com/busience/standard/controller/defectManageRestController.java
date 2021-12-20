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

import com.busience.standard.dto.DefectInfoDto;
import com.busience.standard.service.DefectService;

@RestController("defectManageRestController")
@RequestMapping("defectManageRest")
public class defectManageRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	DefectService defectService;
	
	@GetMapping("/defectManageSelect")
	public List<DefectInfoDto> defectManageSelect() {
		return defectService.selectDefectList();
	}
		
	@PostMapping("/defectManageInsert")
	public int defectManageInsert(DefectInfoDto defectInfoDto) {
		return defectService.insertDefect(defectInfoDto);
	}
	
	@PutMapping("/defectManageUpdate")
	public int defectManageUpdate(DefectInfoDto defectInfoDto) {
		return defectService.updateDefect(defectInfoDto);
	}

	@DeleteMapping("defectManageDelete")
	public int defectManageDelete(DefectInfoDto defectInfoDto) {
		return defectService.deleteDefect(defectInfoDto.getDefect_Code());
	}
}
