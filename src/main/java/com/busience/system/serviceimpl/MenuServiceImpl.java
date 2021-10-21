package com.busience.system.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.system.dao.MenuDao;
import com.busience.system.dto.MenuDto;
import com.busience.system.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	MenuDao menuDao;

	//메뉴 리스트
	@Override
	public List<MenuDto> menuList() {
		final List<MenuDto> menuList = menuDao.listDao();
        return menuList;
	}
	
}
