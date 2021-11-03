package com.busience.common.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.common.dao.MenuDao;
import com.busience.common.dto.MenuDto;
import com.busience.common.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	MenuDao menuDao;

	//유저별 메뉴 list
	@Override
	public List<MenuDto> menuList(String User_Code) {
		return menuDao.menuListDao(User_Code);
	}
}
