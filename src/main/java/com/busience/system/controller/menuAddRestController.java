package com.busience.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.MenuDto;
import com.busience.system.service.MenuAddService;

@RestController("menuAddRestController")
@RequestMapping("menuAddRest")
public class menuAddRestController {

	@Autowired
	MenuAddService menuAddService;
	
	//MA_Search
	@GetMapping("/MA_Search")
	public List<MenuDto> MA_Search() {
		return menuAddService.menuAddList();
	}
	
	//MA_Save
	@PostMapping("/MA_Save")
	public int MA_Save(MenuDto menuDto) {
		return menuAddService.menuAddInsert(menuDto);
	}
}
