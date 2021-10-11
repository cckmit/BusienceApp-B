package com.busience.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.domain.Menu;
import com.busience.common.mapper.MenuMapper;
import com.busience.common.persistence.MenuRepository;

@Service
@Transactional
public class MenuService {
		
	//jpa
	@Autowired
	MenuRepository menuRepository;
	
	public List<Menu> findAll() {
		List<Menu> Menus = new ArrayList<>();
		menuRepository.findAll().forEach(e -> Menus.add(e));
		return Menus;
	} 
	
	//mybatis
	private MenuMapper menuMapper;
			
	public MenuService(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}
		
	//가입시 메뉴 저장
    public int insertMenuNewUser(String string) {
    	return menuMapper.insertMenuNewUser(string);
    }

}
