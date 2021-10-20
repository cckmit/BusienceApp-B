package com.busience.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busience.common.domain.Menu;
import com.busience.common.mapper.MenuMapper;

@Service
public class MenuService {

	private MenuMapper menuMapper;
			
	public MenuService(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}
	
	//메뉴 리스트
	public List<Menu> getAllmenu() {
		final List<Menu> menuList = menuMapper.selectMenuList();
        return menuList;
	}
}
