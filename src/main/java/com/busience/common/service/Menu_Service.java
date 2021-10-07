package com.busience.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.domain.Menu;
import com.busience.common.mapper.Menu_Mapper;
import com.busience.common.persistence.MenuRepository;

@Service
@Transactional
public class Menu_Service {
		
	//jpa
	@Autowired
	MenuRepository menuRepository;
	
	public List<Menu> findAll() {
		List<Menu> Menus = new ArrayList<>();
		menuRepository.findAll().forEach(e -> Menus.add(e));
		return Menus;
	} 
	
	//mybatis
	private Menu_Mapper menu_Mapper;
			
	public Menu_Service(Menu_Mapper menu_Mapper) {
		this.menu_Mapper = menu_Mapper;
	}
		
	//가입시 메뉴 저장
    public int insertMenuNewUser(String string) {
    	return menu_Mapper.insertMenuNewUser(string);
    }

}
