package com.busience.common.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.busience.common.mapper.Menu_Mapper;

@Service
@Transactional
public class Menu_Service {
	
	private Menu_Mapper menu_Mapper;
	
	public Menu_Service(Menu_Mapper menu_Mapper) {
		this.menu_Mapper = menu_Mapper;
	}
	
	//가입시 메뉴 저장
    public int insertMenuNewUser(String string) {
    	return menu_Mapper.insertMenuNewUser(string);
    }

}
