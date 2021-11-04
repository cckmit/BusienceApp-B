package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.MenuDao;
import com.busience.common.dto.MenuDto;

@Service
public class MenuService {

	@Autowired
	MenuDao menuDao;

	//유저별 메뉴 list
	public List<MenuDto> menuList(String User_Code) {
		return menuDao.menuListDao(User_Code);
	}
}
