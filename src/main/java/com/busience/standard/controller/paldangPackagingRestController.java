package com.busience.standard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.PaldangPackagingStandardDto;
import com.busience.standard.service.PaldangPackagingStandardService;

@RestController("paldangPackagingRestController")
@RequestMapping("paldangPackagingRest")
public class paldangPackagingRestController {

	@Autowired
	PaldangPackagingStandardService paldangPackagingService;
	
	@GetMapping("/paldangPackagingList")
	public List<PaldangPackagingStandardDto> paldangPackagingListDao() {
		return paldangPackagingService.paldangPackagingListDao();
	}
	
	@PostMapping("/paldangPackaginInsert")
	public int paldangPackaingInsertDao(@RequestBody List<PaldangPackagingStandardDto> dataList, PaldangPackagingStandardDto paldangPackagingStandardDto) {
		return paldangPackagingService.paldangPackaingInsertDao(dataList, paldangPackagingStandardDto);
	}
	
	@PostMapping("/paldangPackaginDelete")
	public int paldangPackaingDeleteDao(@RequestBody List<PaldangPackagingStandardDto> dataList, PaldangPackagingStandardDto paldangPackagingStandardDto) {
		return paldangPackagingService.paldangPackaingDeleteDao(dataList, paldangPackagingStandardDto);
	}
}
