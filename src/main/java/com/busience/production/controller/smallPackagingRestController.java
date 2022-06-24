package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.Small_Packaging_tbl;
import com.busience.production.service.SmallPackagingService;

@RestController("smallPackagingRestController")
@RequestMapping("smallPackagingRest")
public class smallPackagingRestController {

	@Autowired
	SmallPackagingService smallPackagingService;
	
	@GetMapping("/smallPackagingList")
	public List<Small_Packaging_tbl> smallPackagingListSelect(SearchDto searchDto) {
		return smallPackagingService.smallPackagingListSelect(searchDto);
	}
}
